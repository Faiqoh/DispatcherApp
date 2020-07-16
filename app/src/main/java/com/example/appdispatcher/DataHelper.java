package com.example.appdispatcher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notifikasi.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table notif(no integer primary key autoincrement, title text null, message text null);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
        /*sql = "INSERT INTO notif (no, title, message) VALUES ('1', 'Rama Agastya', 'Job Approved');";
        db.execSQL(sql);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }
}
