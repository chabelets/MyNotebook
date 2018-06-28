package com.example.tom.mynotebook.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.tom.mynotebook.R;
import com.example.tom.mynotebook.engines.NoteEngine;
import com.example.tom.mynotebook.models.NoteEntity;

import java.io.File;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_NOTE_ID = "KEY_NOTE_ID";
    private static final int PERMISSION_REQUEST_CODE = 111;
    private static final int CAMERA_PIC_REQUEST = 1112;
    private EditText headlineEditText;
    private EditText noteTextEditText;
    private long mNoteId = -1;
    File directory;

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



        final Button button1 = findViewById(R.id.makePhotoButtonEditorActivity);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPermissions()){
                    // our app has permissions.
                    makePhoto();
                    createDirectory();


                }
                else {
                    //our app doesn't have permissions, So i m requesting permissions.
                    requestPermissionWithRationale();
//                    button1.setVisibility(View.VISIBLE);
                }
            }
        });
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

    private void makePhoto(){

        Intent cameraIntent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

    }


    private boolean hasPermissions(){
        int res;
        //string array of permissions,
        String[] permissions = new String[]
                {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions){
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return false;
            }
        }
        return true;
    }

    private void requestPerms(){
        String[] permissions = new String[]
                {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,PERMISSION_REQUEST_CODE);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            makePhoto();
            return;
        }
        if (requestCode == CAMERA_PIC_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    Log.d("vetal", "Intent is null");
                } else {
                    Log.d("vetal", "Photo uri: " + data.getData());
                    Bundle bndl = data.getExtras();
                    if (bndl != null) {
                        Object obj = data.getExtras().get("data");
                        if (obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) obj;
                            Log.d("vetal", "bitmap " + bitmap.getWidth() + " x "
                                    + bitmap.getHeight());
                            ImageView ivPhoto = findViewById(R.id.photoImageViewEditorActivity);
                            ivPhoto.setImageBitmap(bitmap);
                        }
                    }
                }

//            Bitmap thumbnail= (Bitmap) data.getExtras().get("data");
//            ImageView image = findViewById(R.id.photoImageViewEditorActivity);
//            image.setImageBitmap(thumbnail);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed) {
            //user granted all permissions we can perform our task.
            makePhoto();
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }
    }

    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(EditorActivity.this.findViewById(R.id.activity_editor), "Storage permission isn't granted" , Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();

                        Toast.makeText(getApplicationContext(),
                                "Open Permissions and grant the Storage permission",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }

    public void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            final String message = "Storage permission is needed to show files count";
            Snackbar.make(EditorActivity.this.findViewById(R.id.activity_editor), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        }
    }

    private void createDirectory() {
        File directory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyFolder");
        if (!directory.exists()) {
            Boolean ff = directory.mkdirs();
            if (ff){
                Toast.makeText(EditorActivity.this, "Folder created successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditorActivity.this, "Failed to create folder", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(EditorActivity.this, "Folder already exist", Toast.LENGTH_SHORT).show();

        }
    }

    private Uri generateFileUri() {

        File file = new File(directory.getPath() + "/" + "photo_"
                        + System.currentTimeMillis() + ".jpg");

        Log.d ("vetal", "fileName = " + file);
        return Uri.fromFile(file);
    }


}



