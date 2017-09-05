package com.zh.young.codeeditor.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


import com.zh.young.codeeditor.entity.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 用于获取文件信息数据
 */

public class FileFindService extends IntentService {
    private ArrayList<File> lists = new ArrayList<>();
    private Messenger mMessenger;
    private Messenger mClientMessenger;
    private Handler mHandleMessage = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mClientMessenger = msg.replyTo;
        }
    };
    public FileFindService() {
        super("");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FileFindService(String name) {
        super(name);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("FileFindService", "服务启动");
        //这个对象只为了携带客户端的信息
        mMessenger = new Messenger(mHandleMessage);

    }

    //寻找文件，然后通过broadcast发出去
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String whoCall = intent.getStringExtra("whoCall");
        switch (whoCall){
            case Constants.NEW_FILE_ACTIVITY :
                processNewFileActivityRequest(intent);
                break;
            case Constants.OPEN_FILE_ACTIVITY :
                processOpenFileActivityRequest(intent);
                break;
        }



    }

    /**
     * 处理打开文件的请求
     * @param intent
     */
    private void processOpenFileActivityRequest(Intent intent) {
       //这里要进行区分文件和文件夹了，因为展示的需要
        File file = (File) intent.getSerializableExtra("file");
        if (lists.size() > 0)
            lists.clear();
        if(file.listFiles().length > 0){
            Collections.addAll(lists,file.listFiles());
            Log.i("process","添加成功"+lists.size());
        }

        Message msg = Message.obtain();
        msg.obj = lists;
        try {
            Log.i("mHandleMessage","数据已发送" + lists.size());
            mClientMessenger.send(msg);

        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    /**
     * 处理新建命令的操作
     * @param intent
     */
    private void processNewFileActivityRequest(Intent intent) {
        File file = (File) intent.getSerializableExtra("file"); //这里应该不会为空的，文件也应该不会为空，要不然是进不来的
        //如果file是文件夹并且内部有其他的子File，那么就添加，否则就显示没有数据
        if(file.isDirectory() && file.listFiles().length > 0){
            Collections.addAll(lists, file.listFiles());
        }else{
            lists.clear();
        }
        Message msg = Message.obtain();
        msg.obj = lists;
        try {
            Log.i("mHandleMessage","数据已发送" + lists.size());
            mClientMessenger.send(msg);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

}
