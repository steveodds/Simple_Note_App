package com.example.simplenotes;


import androidx.annotation.NonNull;

import java.util.Calendar;

public class Note {
    private String mNoteTitle;
    private String mNoteContent;
    private Calendar mCreationDate;
    private Calendar mModifiedDate;
    private Calendar mReminder;
    private boolean mHasReminder;


    Note(){
        mReminder = Calendar.getInstance();
        mCreationDate = Calendar.getInstance();
        mModifiedDate = Calendar.getInstance();
    }

    Note(String note_title, String note_content, boolean hasReminder) {
        if (note_title == null || note_content == null)
            throw new IllegalArgumentException("You cannot save an empty note");
        mNoteTitle = note_title;
        mNoteContent = note_content;
        mHasReminder = hasReminder;
        mReminder = Calendar.getInstance();
        mCreationDate = Calendar.getInstance();
        mModifiedDate = Calendar.getInstance();
    }

    //GETTERS
    Calendar getmReminder() {
        return mReminder;
    }
    Calendar getmCreationDate() { return mCreationDate; }
    Calendar getmModifiedDate() { return mModifiedDate; }
    String getmNoteTitle(){ return mNoteTitle; }
    String getmNoteContent(){ return mNoteContent; }

    //SETTERS
    public void setmNoteTitle(String mNoteTitle) { this.mNoteTitle = mNoteTitle; }
    public void setmNoteContent(String mNoteContent) { this.mNoteContent = mNoteContent; }
    public void setmCreationDate(Calendar mCreationDate) { this.mCreationDate = mCreationDate; }
    public void setmModifiedDate(Calendar mModifiedDate) { this.mModifiedDate = mModifiedDate; }
    public void setmHasReminder(boolean mHasReminder) { this.mHasReminder = mHasReminder; }
    public void setmReminder(Calendar mReminder) { this.mReminder = mReminder; }


    @NonNull
    @Override
    public String toString() {
        if (mNoteTitle == null || mNoteContent == null)
            throw new IllegalArgumentException("Cannot share an empty note.");
        return mNoteTitle + ": " + mNoteContent;
    }
}
