package com.zh.young.codeeditor.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zh.young.codeeditor.Adapters.RecycleViewAdapter;
import com.zh.young.codeeditor.Exception.FileSystemNotMount;
import com.zh.young.codeeditor.R;
import com.zh.young.codeeditor.entity.Constants;
import com.zh.young.codeeditor.entity.OperationManager;
import com.zh.young.codeeditor.entity.StringRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * 编辑器界面
 */

public class EditActivity extends AppCompatActivity implements View.OnClickListener, RecycleViewAdapter.OnRecyclerViewItemClickListener {

    private DrawerLayout drawer;
    private EditText mEtPanel;
    private boolean isEnable = false;
    private ImageButton mMenuButton;
    private ImageButton mSaveButton;
    private ImageButton mUndoButton;
    private ImageButton mRedoButton;
    private ImageButton mEditButton;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.REQUEST_OPEN_DRAWER:
                    drawer.openDrawer(Gravity.START);
                    break;
            }
        }
    };
    private TextView mDisplayFileName;
    private File mResultFile;
    private InputMethodManager mManager;
    private StringRecord mRecord;
    private OperationManager mWatcher;
    private ArrayList<File> datas = new ArrayList();
    private RecycleViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViews();

        try {
            readObjectFromCache();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_OPEN_DRAWER;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task, 800);
        mManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

    }

    private void findViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.menuBar);
        View view = View.inflate(this, R.layout.toolbar_layout, null);
        toolbar.addView(view);
        mMenuButton = (ImageButton) view.findViewById(R.id.menuButton);
        mSaveButton = (ImageButton) view.findViewById(R.id.saveButton);
        mUndoButton = (ImageButton) view.findViewById(R.id.undoButton);
        mRedoButton = (ImageButton) view.findViewById(R.id.redoButton);
        mEditButton = (ImageButton) view.findViewById(R.id.editButton);
        mDisplayFileName = (TextView) view.findViewById(R.id.display_file_name);

        mMenuButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mUndoButton.setOnClickListener(this);
        mRedoButton.setOnClickListener(this);
        mEditButton.setOnClickListener(this);


        mEtPanel = (EditText) findViewById(R.id.et_panel);
        mWatcher = new OperationManager();
        mEtPanel.addTextChangedListener(mWatcher);
        drawer = (DrawerLayout) findViewById(R.id.drawer);


        View slideMenu = drawer.getChildAt(1);
        slideMenu.findViewById(R.id.new_file_command).setOnClickListener(this);
        slideMenu.findViewById(R.id.open_file_command).setOnClickListener(this);
        slideMenu.findViewById(R.id.setting_soft_command).setOnClickListener(this);
        slideMenu.findViewById(R.id.about_soft_command).setOnClickListener(this);
        RecyclerView lists = (RecyclerView) slideMenu.findViewById(R.id.rv_display_history);
        mAdapter = new RecycleViewAdapter(datas);
        lists.setAdapter(mAdapter);
        lists.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menuButton:
                if (drawer.isDrawerOpen(Gravity.START)) {
                    drawer.closeDrawer(Gravity.START);
                } else {
                    drawer.openDrawer(Gravity.START);

                }
//                Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.editButton:
                //如果现在处于可编辑状态那么置为不可编辑，否则置为可编辑
                //改变编辑按钮的颜色，如果处于可编辑状态，那么置为灰色，否则置为黑色
                if(mResultFile == null)
                    break;
                if (isEnable) {
                    mEtPanel.setEnabled(false);
                    mEditButton.setImageResource(R.drawable.can_not_write);
                } else {
                    mEtPanel.setEnabled(true);
                    mEtPanel.requestFocus();
                    mManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mEditButton.setImageResource(R.drawable.writable);
                }
                isEnable = !isEnable;
                break;
            case R.id.undoButton:
                if(mResultFile == null)
                    break;
                  mWatcher.undo(mEtPanel);
                break;
            case R.id.redoButton:
                if(mResultFile == null)
                    break;
                mWatcher.redo(mEtPanel);
                break;
            case R.id.saveButton:
                if(mResultFile == null)
                    break;
                //1. 获取EditPanel中的数据
                Editable text = mEtPanel.getText();
                //2. 将数据写入到文件
                if (mResultFile != null) {
                    try {
                        FileWriter writer = new FileWriter(mResultFile);
                        writer.write(String.valueOf(text));
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.save_file_alert);
                builder.setMessage(R.string.save_succeed);
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();

                break;
            case R.id.new_file_command:
                File file;
                if (Environment.getExternalStorageState().equals(MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();

                } else {
                    throw new FileSystemNotMount("file system unmounted");
                }

                Intent intent = new Intent(this, NewFileActivity.class);
                intent.putExtra("file", file);
                startActivityForResult(intent, Constants.REQUEST_NEW_FILE_CODE);
                //关闭drawer
                drawer.closeDrawer(Gravity.START);
                break;
            case R.id.open_file_command:
                if (Environment.getExternalStorageState().equals(MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();

                } else {
                    throw new FileSystemNotMount("file system unmounted");
                }

                intent = new Intent(this, OpenFileActivity.class);
                intent.putExtra("file", file);
                drawer.closeDrawer(Gravity.START);
                startActivityForResult(intent, Constants.REQUEST_OPEN_FILE_CODE);
                break;
            case R.id.setting_soft_command:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.about_soft_command:
                intent = new Intent(this, AboutSoftActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_NEW_FILE_CODE:
                    mResultFile = (File) data.getSerializableExtra("file");
                    Toast.makeText(this, mResultFile.getName(), Toast.LENGTH_SHORT).show();
                    //更新文件名和打开文件的可编辑状态
                    mDisplayFileName.setText(mResultFile.getName());
                    mEtPanel.setEnabled(true);
                    mEditButton.setImageResource(R.drawable.writable);
                    mEtPanel.setText("");
                    //将文件添加到列表中，便于展示
                    if(!datas.contains(mResultFile)){
                        datas.add(mResultFile);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case Constants.REQUEST_OPEN_FILE_CODE:
                    mResultFile = (File) data.getSerializableExtra("file");
                    mDisplayFileName.setText(mResultFile.getName());
                    mEtPanel.setEnabled(true);
                    mEditButton.setImageResource(R.drawable.writable);
                    // TODO 读取数据，显示到编辑面板上
                    String text = "";
                    try {

                        text = readFromFile(mResultFile);
                    } catch (IOException e) {
                        Toast.makeText(this, "文件读取异常，请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    mEtPanel.setText(text);
                    //设置光标的位置为文字的末端
                    mEtPanel.setSelection(text.length());
                    //将文件添加到列表中，便于展示
                    if(!datas.contains(mResultFile)){
                        datas.add(mResultFile);
                    }

                    mAdapter.notifyDataSetChanged();
                    break;


            }
        }

    }

    private String readFromFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] string = new char[1024];
        FileReader reader = new FileReader(file);
        while (reader.read(string) != -1) {
            sb.append(string);
        }

        return sb.toString();
    }

    @Override
    public void onItemClick(View view, File data) {
        //当点击的时候去打开对应的文件展示到对应的位置
        //更新文件名和打开文件的可编辑状态
        mDisplayFileName.setText(data.getName());
        mEtPanel.setEnabled(true);
        mEditButton.setImageResource(R.drawable.writable);

        String text = "";
        try {

            text = readFromFile(data);
        } catch (IOException e) {
            Toast.makeText(this, "文件读取异常，请稍后再试", Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(Gravity.START);
        mEtPanel.setText(text);
        //设置光标的位置为文字的末端
        mEtPanel.setSelection(text.length());
    }

    @Override
    protected void onDestroy() {
        //在这里将需要存储的数据存储到缓存中
        super.onDestroy();
        try {
            writeObjectToCache();
        } catch (IOException e) {
            Toast.makeText(this, "文件写入异常", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    void writeObjectToCache() throws IOException {
        File cacheDir = getCacheDir();
        File cache = new File(cacheDir, "file_cache");
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(cache));
        for(int i = 0;i < datas.size();i++){
            outputStream.writeObject(datas.get(i));
        }
        outputStream.close();

    }

    void readObjectFromCache() throws IOException, ClassNotFoundException {
        File cacheDir = getCacheDir();
        File cache = new File(cacheDir, "file_cache");
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(cache));
        File temp;
        while((temp = (File) inputStream.readObject()) != null){
            datas.add(temp);
        }
    }
}
