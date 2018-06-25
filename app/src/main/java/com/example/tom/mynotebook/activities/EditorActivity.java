package com.example.tom.mynotebook.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tom.mynotebook.R;
import com.example.tom.mynotebook.engines.NoteEngine;
import com.example.tom.mynotebook.models.NoteEntity;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_NOTE_ID = "KEY_NOTE_ID";
    private EditText headlineEditText;
    private EditText noteTextEditText;
    private long mNoteId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        headlineEditText = findViewById(R.id.headlineEditTextEditorActivity);
        noteTextEditText = findViewById(R.id.noteTextEditTextEditorActivity);

        Button saveButton = findViewById(R.id.saveButtonEditorActivity);
        saveButton.setOnClickListener(this);

        Button removeButton = findViewById(R.id.removeButtonEditorActivity);
        removeButton.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            mNoteId = bundle.getLong(KEY_NOTE_ID, -1);
        }

        if (mNoteId != -1){
            removeButton.setVisibility(View.VISIBLE);
            NoteEntity noteEntity = new NoteEngine(this).getNoteById(mNoteId);
            headlineEditText.setText(noteEntity.getHeadline());
            noteTextEditText.setText(noteEntity.getNoteText());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveButtonEditorActivity:
                String strHeadline = headlineEditText.getText().toString();
                String strNoteText = noteTextEditText.getText().toString();

                if (strHeadline.isEmpty()){
                    Toast.makeText(EditorActivity.this, "Headline is empty",
                            Toast.LENGTH_LONG).show();
                }

                NoteEntity noteEntity = new NoteEntity(mNoteId, strHeadline, strNoteText );
                NoteEngine noteEngine = new NoteEngine(EditorActivity.this);

                if (mNoteId != -1){
                    noteEngine.updateNote(noteEntity);
                    finish();
                } else {
                    noteEngine.insertNote(noteEntity);
                    headlineEditText.setText("");
                    noteTextEditText.setText("");
                    headlineEditText.requestFocus();
                }
                break;
            case R.id.removeButtonEditorActivity:
                new NoteEngine(this).removeNoteById(mNoteId);
                finish();
                break;
        }
    }

}



