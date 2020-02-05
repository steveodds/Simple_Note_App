package com.example.simplenotes;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NotesHandler {
    private ArrayList<Note> mNotes = new ArrayList<>();

    ArrayList<Note> getmNotes() { return mNotes; }

    NotesHandler() {

    }

    public NotesHandler(ArrayList<Note> mNotes) {
        this.mNotes = mNotes;
    }

    void addNoteToList(Note note) {
        if (note == null)
            throw new IllegalArgumentException("Cannot add a null note to the list.");

        mNotes.add(note);
    }

    public void removeNoteFromList(Note note) {
        if (note == null)
            throw new IllegalArgumentException("The list you attempted to remove is null.");

        mNotes.remove(note);
    }

    public int numberOfNotesInList() {
        return mNotes.size();
    }

    public void deleteNotesFromList() {
        mNotes.clear();
    }

    void saveAllNotesToDB(Context context) {
        if (mNotes == null)
            throw new IllegalArgumentException("Cannot save null list.");
        writeNotesToDisk(context);
    }

    public void getAllNotesFromDB(Context context) {
        readAllNotesFromDB(context);
    }

    boolean exists(String noteTitle) {
        for (Note note :
                mNotes) {
            if (note.getmNoteTitle().equals(noteTitle))
                return true;
        }
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder noteTitles = new StringBuilder();

        for (Note note : mNotes) {
            noteTitles.append(note.getmNoteTitle());
        }

        return noteTitles.toString();
    }

    private void writeNotesToDisk(Context context) {
        DBManager db = new DBManager(context);
        for (Note note :
                mNotes) {
            db.WriteToDB(note.getmNoteTitle(), note.getmNoteContent(), note.ismHasReminder());
        }
    }

    private void readAllNotesFromDB(Context context) {
        DBManager fromDB = new DBManager(context);
        mNotes = fromDB.ReadAllFromDB().getmNotes();
    }
}
