package com.example.simplenotes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

public class new_note extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final NotesHandler newlyCreatedNotes = new NotesHandler();

        final EditText noteTitleFromView = findViewById(R.id.new_note_title);
        final EditText noteContentFromView = findViewById(R.id.new_note_content);


        Button saveNoteFromView = findViewById(R.id.save_note_button);
        saveNoteFromView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Note newNote = new Note(noteTitleFromView.getText().toString(), noteContentFromView.getText().toString(), false);
                newlyCreatedNotes.addNoteToList(newNote);

                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "Saved note.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
    }

}
