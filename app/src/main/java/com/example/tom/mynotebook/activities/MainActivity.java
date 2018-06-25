package com.example.tom.mynotebook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.tom.mynotebook.R;
import com.example.tom.mynotebook.adapters.NoteContentAdapter;
import com.example.tom.mynotebook.engines.NoteEngine;
import com.example.tom.mynotebook.models.NoteEntity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteContentAdapter.OnClickListener {

    private RecyclerView recyclerView;
    private NoteContentAdapter adapter;
    private Button addButton;
    ArrayList<NoteEntity> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.containerRecyclerViewMainActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));
        adapter = new NoteContentAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);

        addButton = findViewById(R.id.addNoteButtonMainActivity);
        addButton.setOnClickListener(createAddNoteButtonListener());
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
        NoteEngine noteEngine = new NoteEngine(this);
        notes = (ArrayList<NoteEntity>) noteEngine.getAllNotes();
        adapter.updateDataOnScreen(notes);
    }

    @Override
    public void onClickItem(long id) {
        Intent editActivityIntent = new Intent(this, EditorActivity.class);
        editActivityIntent.putExtra(EditorActivity.KEY_NOTE_ID, id);
        startActivity(editActivityIntent);
    }

}
