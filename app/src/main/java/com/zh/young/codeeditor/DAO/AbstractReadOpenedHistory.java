package com.zh.young.codeeditor.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public abstract  class AbstractReadOpenedHistory extends SQLiteOpenHelper {
    public AbstractReadOpenedHistory(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
