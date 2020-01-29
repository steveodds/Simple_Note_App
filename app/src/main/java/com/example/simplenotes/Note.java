package com.example.simplenotes;


import androidx.annotation.NonNull;

import java.util.Date;

public class Note {
    private String mNoteTitle;
    private String mNoteContent;
    private Date mCreationDate;
    private Date mModifiedDate;
    private Date mReminder;
    private boolean mHasReminder;


    public Note(String note_title, String note_content, boolean hasReminder) {
        if (note_title == null || note_content == null)
            throw new IllegalArgumentException("You cannot save an empty note");
        mNoteTitle = note_title;
        mNoteContent = note_content;
        mHasReminder = hasReminder;
        mReminder = new Date();
    }

    public void setNoteSaveDate(){
        mCreationDate = new Date();
    }

    public void setModifiedDate(){
        mModifiedDate = new Date();
    }

    public Date getmReminder() {
        return mReminder;
    }

    public void setmReminder(Date mReminder) {
        this.mReminder = mReminder;
    }

    @NonNull
    @Override
    public String toString() {
        if (mNoteTitle == null || mNoteContent == null)
            throw new IllegalArgumentException("Cannot share an empty note.");
        return mNoteTitle + ": " + mNoteContent;
    }

    public Date getmCreationDate() {
        return mCreationDate;
    }

    public Date getmModifiedDate() {
        return mModifiedDate;
    }
}
