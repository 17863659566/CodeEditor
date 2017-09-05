package com.zh.young.codeeditor.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 用于存储已经打开的文件名和文件地址，以便于下次打开
 */

 class ReadOpenedHistory extends AbstractReadOpenedHistory {

    private String TABLE_NAME = "openHistory";
    private String FILE_NAME = "filename";
    private String FILE_PATH = "filepath";
    private String _ID = "_id";
    private String SQL = "CREATE TABLE " + TABLE_NAME + " ("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FILE_NAME+" TEXT, "+FILE_PATH+" TEXT)";
    public ReadOpenedHistory(Context context, String name , SQLiteDatabase.CursorFactory factory, int version) {
       super(context, "openHistoryDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
