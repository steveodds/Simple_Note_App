package com.example.simplenotes;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NotesHandler {
    private ArrayList<Note> mNotes = new ArrayList<>();

    NotesHandler() {

    }

    public NotesHandler(ArrayList<Note> mNotes) {
        this.mNotes = mNotes;
    }

    void addNoteToList(Note note){
        if (note == null)
            throw new IllegalArgumentException("Cannot add a null note to the list.");

        mNotes.add(note);
    }

    public void removeNoteFromList(Note note){
        if (note == null)
            throw new IllegalArgumentException("The list you attempted to remove is null.");

        mNotes.remove(note);
    }

    public int numberOfNotesInList(){
        return mNotes.size();
    }

    public void deleteNotesFromList(){
        mNotes.clear();
    }

    public void saveAllNotesToDB(){
        if (mNotes == null)
            throw new IllegalArgumentException("Cannot save null list.");
        writeNotesToDisk();
    }

    public void getAllNotesFromDB(int numberOfNotes){
        if (numberOfNotes == 0) {
            readAllNotesFromDB();
        }
        else {
            readNotesFromDB(numberOfNotes);
        }
    }

    boolean exists(String noteTitle){
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

        for (Note note: mNotes) {
            noteTitles.append(note.getmNoteTitle());
        }

        return noteTitles.toString();
    }

    private void writeNotesToDisk() {
        //TODO Add logic for passing the list to be saved
    }

    private void readNotesFromDB(int numberOfNotes) {
        //TODO Read records of notes starting from the index
    }

    private void readAllNotesFromDB() {
        //TODO Read all records of notes and populate the ArrayList
    }
}
