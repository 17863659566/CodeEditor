package com.zh.young.codeeditor.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zh.young.codeeditor.Adapters.RecycleViewAdapter;
import com.zh.young.codeeditor.R;
import com.zh.young.codeeditor.entity.Constants;
import com.zh.young.codeeditor.service.FileFindService;
import com.zh.young.codeeditor.views.PullDownListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import android.os.Handler;
import android.widget.Toast;

public class NewFileActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 创建文件夹按钮
     */
    private ImageButton mCreateFolderButton;
    /**
     * 文件的列表
     */
    private RecyclerView mFileList;
    /**
     * 展示当前文件的路径
     */
    private TextView mDisplayPath;
    /**
     * 用来存储文件的信息
     */
    public File data;
    private boolean mBindService;
    private ArrayList<File> mFileLists = new ArrayList<>();
    private Handler mHandleMessage = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.i("mHandleMessage","数据已接受");
            mFileLists.clear();
            mFileLists.addAll((Collection<? extends File>) msg.obj);
            adapter.notifyDataSetChanged();
            //Log.i("mFileList",mFileLists.hashCode()+"");

        }
    };
    private RecycleViewAdapter adapter;
    private ServiceConnection mConnection;
    private EditText mFileNameET;
    private String mFileType;
    private PullDownListView mFileTypePDLV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_file);
        Log.i("线程",Thread.currentThread().getName());
        /*
         * 在本活动创建的时候去开启服务获取第一层的文件列表，不在主线程内做这个活动
         */
        Intent requestIntent = getIntent();
        File file = (File) requestIntent.getSerializableExtra("file");
        mFileLists.add(file);
        findViews();
        final Messenger messenger = new Messenger(mHandleMessage);
        mConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Messenger fileService = new Messenger(service);
                Message msg = Message.obtain();
                msg.replyTo = messenger;
                try {
                    fileService.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };


    }

    private void findViews() {

        View exploreFile = findViewById(R.id.explore_file);
        mCreateFolderButton = (ImageButton) exploreFile.findViewById(R.id.createFolderButton);
        mFileList = (RecyclerView) exploreFile.findViewById(R.id.file_list);

        mDisplayPath = (TextView) exploreFile.findViewById(R.id.display_path);
        adapter = new RecycleViewAdapter(mFileLists);
        Log.i("mFileList",mFileLists.hashCode()+"");
        mFileList.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mFileList.setLayoutManager(manager);
        adapter.setOnItemClickListener(new RecycleViewAdapter.OnRecyclerViewItemClickListener() {



            @Override
            public void onItemClick(View view, File data) {
                NewFileActivity.this.data = data;
                mDisplayPath.setText(data.getAbsolutePath());
                Intent intent = new Intent(NewFileActivity.this, FileFindService.class);
                intent.putExtra("file",data);
                intent.putExtra("whoCall", Constants.NEW_FILE_ACTIVITY);
                Log.i("onItemClick",data.getName());
                startService(intent);
                mBindService = bindService(intent, mConnection, BIND_AUTO_CREATE);


            }
        });

        findViewById(R.id.save_file).setOnClickListener(this);
        findViewById(R.id.encrypt_file_name).setOnClickListener(this);
        mFileNameET = (EditText) findViewById(R.id.file_name);
        mFileTypePDLV = ((PullDownListView) findViewById(R.id.file_type));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBindService){
            unbindService(mConnection);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_file :
                //TODO 获取文本框内的文件名称和弹框内的文件类型，然后组合成文件名，存储起来后，跳到编辑界面
                Editable fileName = mFileNameET.getText();
                String fileType = mFileTypePDLV.getFileType();
                if(data == null){
                    Toast.makeText(this, "请选择保存位置", Toast.LENGTH_SHORT).show();
                }else{
                    String filePath = data.getAbsolutePath()+"/" + fileName + fileType;
                    File file = new File(filePath);

                    //开启编辑界面
                    Intent intent = new Intent();
                    intent.putExtra("file",file);
                    setResult(RESULT_OK,intent);
                    finish();
                }


                break;
            case R.id.encrypt_file_name :
                break;
        }
    }
}
