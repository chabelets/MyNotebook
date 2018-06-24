package com.example.tom.mynotebook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tom.mynotebook.R;
import com.example.tom.mynotebook.adapters.NoteContentAdapter;
import com.example.tom.mynotebook.db.AppSettings;
import com.example.tom.mynotebook.engines.NoteEngine;
import com.example.tom.mynotebook.models.NoteEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NoteContentAdapter.OnClickListener {

    private RecyclerView recyclerView;
    private NoteContentAdapter adapter;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppSettings appSettings = new AppSettings(this);

        recyclerView = findViewById(R.id.containerRecyclerViewMainActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        adapter = new NoteContentAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);

        addButton = findViewById(R.id.addNoteButtonMainActivity);
        addButton.setOnClickListener(createAddNoteButtonListener());

        if (appSettings.isFirstStart()){
            appSettings.setIsFirstStart(false);
            Toast.makeText(this, "FIRST START", Toast.LENGTH_LONG).show();

            NoteEngine noteEngine = new NoteEngine(this);
            for (int i = 0; i < 100; i++) {
                NoteEntity noteEntity = new NoteEntity( i, "headline_" + i, "text_" + i);
                noteEngine.insertNote(noteEntity);
            }
        }
    }

    private View.OnClickListener createAddNoteButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditorActivity.class));
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDataOnScreen();
    }

    private void updateDataOnScreen(){

        Log.d("vet", "name1 -> " + Thread.currentThread().getName());

        NoteEngine noteEngine = new NoteEngine(this);
        noteEngine.getAllNotes(new NoteEngine.AllNotesData() {
            @Override
            public void onLoadAllUserData(ArrayList<NoteEntity> data) {
                adapter.updateDataOnScreen();
            }
        });
    }

    @Override
    public void onClickItem(long id) {

    }
}
