package com.example.tom.mynotebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tom.mynotebook.constants.DBConstants;

public class AppDBHelper extends SQLiteOpenHelper {

    public AppDBHelper(Context context) {
        super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBConstants.DB_TABLE_NAME +
                "( " + DBConstants.DB_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        DBConstants.DB_HEADLINE + " TEXT NOT NULL, " +
        DBConstants.DB_NOTE_TEXT + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
