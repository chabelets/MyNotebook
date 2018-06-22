package com.example.tom.mynotebook.engines;

import android.content.Context;

import com.example.tom.mynotebook.models.NoteEntity;
import com.example.tom.mynotebook.wrappers.NoteDBWrapper;

import java.util.ArrayList;
import java.util.List;

public class NoteEngine {

    private Context mContext;

    public NoteEngine(Context mContext) {
        this.mContext = mContext;
    }



    public NoteEntity getNoteById(long nId){
        NoteDBWrapper noteDBWrapper = new NoteDBWrapper(mContext);
        return noteDBWrapper.getNoteById(nId);
    }

    public void insertUser(NoteEntity user){
        NoteDBWrapper noteDBWrapper = new NoteDBWrapper(mContext);
        noteDBWrapper.insertUser(user);
    }

    public void getAllNotes(AllNotesData callback){
//        NoteDBWrapper noteDBWrapper = new NoteDBWrapper(getContext());
//        ArrayList<NoteEntity> noteEntities = noteDBWrapper.getAllNotes();
    }

    public interface AllNotesData {
        void onLoadAllUserData(ArrayList<NoteEntity> data);
    }
}
