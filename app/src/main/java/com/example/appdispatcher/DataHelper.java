package com.example.appdispatcher;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notifikasi.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "notif";
    private static final String COLUMN_NO = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_MESSAGE = "message";
    private static final String COLUMN_DATE = "created_at";
    private Context context;

    private static final String DATABASE_ALTER_TEAM_1 = "ALTER TABLE "
            + TABLE_NAME + " ADD COLUMN " + COLUMN_DATE + " DATETIME DEFAULT '2021-01-01 00:00:00';";


    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_MESSAGE + " TEXT, "  +
                COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (oldVersion < 2) {
            db.execSQL(DATABASE_ALTER_TEAM_1);
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NO + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
