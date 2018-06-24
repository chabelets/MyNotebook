package com.example.tom.mynotebook.engines;

import android.content.Context;

import com.example.tom.mynotebook.models.NoteEntity;
import com.example.tom.mynotebook.wrappers.NoteDBWrapper;

import java.util.ArrayList;

public class NoteEngine {

    private Context mContext;

    public NoteEngine(Context mContext) {
        this.mContext = mContext;
    }

    public NoteEntity getNoteById(long nId){
        NoteDBWrapper noteDBWrapper = new NoteDBWrapper(mContext);
        return noteDBWrapper.getNoteById(nId);
    }

    public void insertNote(NoteEntity note){
        NoteDBWrapper noteDBWrapper = new NoteDBWrapper(mContext);
        noteDBWrapper.insertNote(note);
    }

    public void getAllNotes(AllNotesData callback){
//        NoteDBWrapper noteDBWrapper = new NoteDBWrapper(getContext());
//        ArrayList<NoteEntity> noteEntities = noteDBWrapper.getAllNotes();
    }

    public void updateNote(NoteEntity note){
        NoteDBWrapper noteDBWrapper = new NoteDBWrapper(mContext);
        noteDBWrapper.updateNote(note);
    }

    public interface AllNotesData {
        void onLoadAllUserData(ArrayList<NoteEntity> data);
    }
}
