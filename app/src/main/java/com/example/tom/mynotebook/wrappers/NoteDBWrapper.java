package com.example.tom.mynotebook.wrappers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tom.mynotebook.constants.DBConstants;
import com.example.tom.mynotebook.models.NoteEntity;

import java.util.ArrayList;

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
        NoteEntity result = null;
        SQLiteDatabase db = getReadableDB();
        String strSelection = DBConstants.DB_FIELD_ID + "=?";
        String[] argSelection = new String[]{Long.toString(nId)};
        Cursor cursor = db.query(getTableName(), null, strSelection, argSelection,
                null, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                int nPositionHeadline = cursor.getColumnIndex(DBConstants.DB_FIELD_HEADLINE);
                int nPositionNoteText = cursor.getColumnIndex(DBConstants.DB_FIELD_NOTE_TEXT);

                String strHeadline = cursor.getString(nPositionHeadline);
                String strNoteText = cursor.getString(nPositionNoteText);

                result = new NoteEntity(strHeadline, strNoteText);
            }
            cursor.close();
        }
        db.close();
        return result;
    }

    public void insertNote(NoteEntity note) {
        SQLiteDatabase db = getWritableDB();
        db.insert(getTableName(), null, note.getContentValues());
        db.close();
    }

    public void updateNote(NoteEntity note){
        SQLiteDatabase db = getWritableDB();
        String strSelection = DBConstants.DB_FIELD_ID + "=?";
        String[] argSelection = new String[]{Long.toString(note.getNoteId())};
        db.update(getTableName(), note.getContentValues(), strSelection, argSelection);
        db.close();
    }

    public void removeUser (long nId){
        SQLiteDatabase db = getWritableDB();
        String strSelection = DBConstants.DB_FIELD_ID + "=?";
        String[] argSelection = new String[]{Long.toString(nId)};
        db.delete(getTableName(), strSelection, argSelection);
        db.close();
    }
}

