package com.zh.young.codeeditor.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.zh.young.codeeditor.entity.FileHistory;

import java.io.IOException;
import java.util.Properties;

/**
 * 对SQLite数据库进行操作,设计为单例模式,使用双重锁模式
 */

public class DAOUtil {
    private static DAOUtil daoUtil;
    private ReadOpenedHistory historyDB;
    private String tableName;

    private DAOUtil(){
    }

    private DAOUtil(Context context) throws IOException {
        historyDB = new ReadOpenedHistory(context, null, null, 1);
        Properties properties = new Properties();
        properties.load(context.getAssets().open("config.properties"));
        tableName = properties.getProperty("TABLE_NAME");
        properties.clear();
    }

    public static DAOUtil getInstance(Context context) throws IOException {
        if(daoUtil == null){
            synchronized (DAOUtil.class){
                if(daoUtil == null){
                    daoUtil = new DAOUtil(context);
                }
            }
        }

        return daoUtil;
    }
    public boolean addHistory(FileHistory history){
        SQLiteDatabase db = historyDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("filename",history.getFilename());
        values.put("filepath",history.getFilepath());
        boolean result = db.insert(tableName, null, values) != -1;
        return result;
    }


    public Cursor readAllHistory(Context context){
        SQLiteDatabase db = historyDB.getWritableDatabase();
        Cursor cursor = db.query(true, tableName, null, null, null, null, null, null, null);
        return cursor;
    }

    public boolean deleteHistory(Context context,FileHistory fileHistory){
        SQLiteDatabase db = historyDB.getWritableDatabase();
        boolean result = db.delete(tableName, null, new String[]{fileHistory.getFilename()}) != 0;
        return result;
    }

    public boolean modifyHistory(Context context,FileHistory fileHistory,FileHistory oldFileHistory){
        SQLiteDatabase db = historyDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(fileHistory.getFilename(),fileHistory.getFilepath());
        boolean result = db.update(tableName, values, null, new String[]{oldFileHistory.getFilename()}) != 0;
        return result;
    }

}
