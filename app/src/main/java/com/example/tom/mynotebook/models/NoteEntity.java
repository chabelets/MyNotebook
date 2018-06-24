package com.example.tom.mynotebook.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.tom.mynotebook.constants.DBConstants;

public class NoteEntity {

    private long mNoteId;
    private String mHeadline;
    private String mNoteText;

    public NoteEntity(Cursor cursor){
        int nPositionId = cursor.getColumnIndex(DBConstants.DB_FIELD_ID);
        int nPositionHeadline = cursor.getColumnIndex(DBConstants.DB_FIELD_HEADLINE);
        int nPositionNoteText = cursor.getColumnIndex(DBConstants.DB_FIELD_NOTE_TEXT);

        this.mNoteId = cursor.getLong(nPositionId);
        this.mHeadline = cursor.getString(nPositionHeadline);
        this.mNoteText = cursor.getString(nPositionNoteText);
    }

    public NoteEntity(long mNoteId, String mHeadline, String mNoteText) {
        this.mNoteId = mNoteId;
        this.mHeadline = mHeadline;
        this.mNoteText = mNoteText;
    }

    public NoteEntity(String mHeadline, String mNoteText){
        this(-1, mHeadline, mNoteText);
    }

    public long getNoteId() {
        return mNoteId;
    }

    public void setNoteId(long mNoteId) {
        this.mNoteId = mNoteId;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public void setHeadline(String mHeadline) {
        this.mHeadline = mHeadline;
    }

    public String getNoteText() {
        return mNoteText;
    }

    public void setNoteText(String mNoteText) {
        this.mNoteText = mNoteText;
    }

    public ContentValues getContentValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.DB_FIELD_HEADLINE, getHeadline());
        contentValues.put(DBConstants.DB_FIELD_NOTE_TEXT, getNoteText());
        return contentValues;
    }
}
