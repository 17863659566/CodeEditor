package com.zh.young.codeeditor.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class OpenFileActivity extends AppCompatActivity {

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
            //Log.i("mFileList",mFileLists.size()+"");

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
        setContentView(R.layout.activity_open_file);
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
                mDisplayPath.setText(data.getAbsolutePath());
                if(data.isFile()){
                    //如果用户点击的是文件，那么直接将文件信息返回就可以了
                    Intent intent = new Intent();
                    intent.putExtra("file",data);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    //否则继续开启服务，去迭代
                    Intent intent = new Intent(OpenFileActivity.this, FileFindService.class);
                    intent.putExtra("file",data);
                    intent.putExtra("whoCall", Constants.OPEN_FILE_ACTIVITY);
                    Log.i("onItemClick",data.getName());
                    startService(intent);
                    mBindService = bindService(intent, mConnection, BIND_AUTO_CREATE);
                }




            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBindService){
            unbindService(mConnection);
        }

    }


}
