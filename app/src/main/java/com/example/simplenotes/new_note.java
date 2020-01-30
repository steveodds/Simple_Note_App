package com.example.simplenotes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Objects;

public class new_note extends AppCompatActivity {

    final NotesHandler newlyCreatedNotes = new NotesHandler();
    EditText noteTitleFromView;
    EditText noteContentFromView;
    Snackbar snackbar;
    Switch reminderSwitch;
    private boolean hasReminder;
    private boolean isEdit;
    Calendar reminderDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        reminderDateTime = Calendar.getInstance();
        noteTitleFromView = findViewById(R.id.new_note_title);
        noteContentFromView = findViewById(R.id.new_note_content);
        reminderSwitch = findViewById(R.id.reminder_switch);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    Calendar reminderDate = fetchReminderDate();
                    setReminder(reminderDate);
                    hasReminder = true;
                } else {
                    reminderDateTime.clear();
                    hasReminder = false;
                    unsetReminder(new Object());
                }
            }
        });


        Button saveNoteFromView = findViewById(R.id.save_note_button);
        saveNoteFromView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(inputManager).hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                //TODO Add reminder functionality
                String title = noteTitleFromView.getText().toString();
                String content = noteContentFromView.getText().toString();

                //TODO Add editing check
                if (!hasEmpties(title, content)){
                    validateThenSave(title, content, hasReminder);
                }
            }
        });
    }

    private void unsetReminder(Object o) {
        //TODO remove reminder
    }

    private void setReminder(Calendar reminderDate) {
        //TODO register reminder with system
    }

    private Calendar fetchReminderDate() {
        Calendar local = Calendar.getInstance();
        int day = local.get(Calendar.DAY_OF_MONTH);
        int month = local.get(Calendar.MONTH);
        int year = local.get(Calendar.YEAR);
        int hour = local.get(Calendar.HOUR_OF_DAY);
        int minute = local.get(Calendar.MINUTE);
        DatePickerDialog datePicker = new DatePickerDialog(new_note.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                reminderDateTime.set(Calendar.YEAR, year);
                reminderDateTime.set(Calendar.MONTH, monthOfYear);
                reminderDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        }, year, month, day);
        datePicker.show();

        TimePickerDialog timePickerDialog = new TimePickerDialog(new_note.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                reminderDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                reminderDateTime.set(Calendar.MINUTE, minute);
            }
        }, hour, minute, false);
        timePickerDialog.show();

        return reminderDateTime;
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
            //TODO Add dates
            newlyCreatedNotes.addNoteToList(newNote);
            return true;
        } catch (Exception e) {
            Log.d("new_note.saveNote", "Failed to save note => " + e);
            return false;
        }
    }

}
