package com.example.simplenotes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class new_note extends AppCompatActivity {

    final NotesHandler newlyCreatedNotes = new NotesHandler();
    EditText noteTitleFromView;
    EditText noteContentFromView;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noteTitleFromView = findViewById(R.id.new_note_title);
        noteContentFromView = findViewById(R.id.new_note_content);


        Button saveNoteFromView = findViewById(R.id.save_note_button);
        saveNoteFromView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasReminder = false; //TODO Add reminder functionality
                String title = noteTitleFromView.getText().toString();
                String content = noteContentFromView.getText().toString();

                if (!hasEmpties(title, content)){
                    validateThenSave(title, content, hasReminder);
                }


            }
        });
    }

    private void validateThenSave(String title, String content, boolean hasReminder) {
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinatorLayout);
        if (newlyCreatedNotes.exists(title.trim())) {
            snackbar = Snackbar.make(coordinatorLayout, "Failed to save: A note with that title already exists", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else {
            boolean isSaved = saveNote(title, content, hasReminder);
            if (isSaved) {
                snackbar = Snackbar.make(coordinatorLayout, "Saved note.", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                snackbar = Snackbar.make(coordinatorLayout, "Failed to save note. please try again later.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    private boolean hasEmpties(String titleText, String contentText) {
        if (titleText.trim().isEmpty()){
            snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "You cannot have an empty title", Snackbar.LENGTH_LONG);
            snackbar.show();
            return true;
        } else if (contentText.trim().isEmpty()){
            snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "You cannot have an empty note body", Snackbar.LENGTH_LONG);
            snackbar.show();
            return true;
        }
        return false;
    }

    private boolean saveNote(String noteTitle, String noteContent, boolean hasReminder) {
        try {
            Note newNote = new Note(noteTitle.trim(), noteContent, hasReminder);
            newlyCreatedNotes.addNoteToList(newNote);
            return true;
        } catch (Exception e) {
            Log.d("new_note.saveNote", "Failed to save note => " + e);
            return false;
        }
    }

}
