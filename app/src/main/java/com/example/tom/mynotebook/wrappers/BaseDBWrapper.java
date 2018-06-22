package com.example.tom.mynotebook.wrappers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.tom.mynotebook.db.AppDBHelper;

public abstract class BaseDBWrapper {

    private AppDBHelper mDBHelper;
    private String mTableName;
    public BaseDBWrapper(Context context, String strTableName){
        mDBHelper = new AppDBHelper(context);
        this.mTableName = strTableName;
    }

    public SQLiteDatabase getWritableDB(){
        return mDBHelper.getWritableDatabase();
    }

    public SQLiteDatabase getReadableDB(){
        return mDBHelper.getReadableDatabase();
    }

    public String getTableName(){
        return mTableName;
    }
}
