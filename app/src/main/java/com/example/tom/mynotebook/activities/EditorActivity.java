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

public class EditorActivity extends AppCompatActivity {

    public static final String KEY_USER_ID = "KEY_USER_ID";
    private EditText headlineEditText;
    private EditText noteTextEditText;
    private long mUserId = -1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        headlineEditText = findViewById(R.id.headlineEditTextEditorActivity);
        noteTextEditText = findViewById(R.id.noteTextEditTextEditorActivity);
        Button saveButton = findViewById(R.id.saveButtonEditorActivity);
        saveButton.setOnClickListener(createSaveButtonListener());


        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            mUserId = bundle.getLong(KEY_USER_ID, -1);
        }

        if (mUserId != -1){
            NoteEntity noteEntity = new NoteEngine(this).getNoteById(mUserId);

            headlineEditText.setText(noteEntity.getHeadline());
            noteTextEditText.setText(noteEntity.getNoteText());

//            addButton.setText("Update");
//            removeButton.setVisibility(View.VISIBLE);

        }
    }

    private View.OnClickListener createSaveButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strHeadline = headlineEditText.getText().toString();
                String strNoteText = noteTextEditText.getText().toString();

                if (strHeadline.isEmpty()){
                    Toast.makeText(EditorActivity.this, "Headline is empty",
                            Toast.LENGTH_LONG).show();
                }

                NoteEntity noteEntity = new NoteEntity(0, strHeadline, strNoteText );

                NoteEngine noteEngine = new NoteEngine(EditorActivity.this);

//                if (mUserId != -1){
//                    noteEngine.updateNote(noteEntity);
//                    finish();
//                } else {
                    noteEngine.insertNote(noteEntity);
                    headlineEditText.setText("");
                    noteTextEditText.setText("");
                    headlineEditText.requestFocus();
//                }


            }
        };
    }
}
