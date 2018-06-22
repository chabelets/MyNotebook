package com.example.tom.mynotebook.wrappers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tom.mynotebook.constants.DBConstants;
import com.example.tom.mynotebook.models.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteDBWrapper extends BaseDBWrapper {

    public NoteDBWrapper(Context context) {
        super(context, DBConstants.DB_TABLE_NAME);
    }

    public ArrayList<NoteEntity> getAllNotes() {
        ArrayList<NoteEntity> result = new ArrayList<>();
        SQLiteDatabase db = getReadableDB();
        if (db != null){
            Cursor cursor = db.query(getTableName(), null, null, null,
                    null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        result.add(0, new NoteEntity(cursor));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
        }
        return result;
    }


    public NoteEntity getNoteById(long nId) {
        return null;
    }


    public void insertUser(NoteEntity note) {
        SQLiteDatabase db = getWritableDB();
        db.insert(getTableName(), null, note.getContentValues());
        db.close();
    }
}
