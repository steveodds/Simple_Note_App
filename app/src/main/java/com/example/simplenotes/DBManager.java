package com.example.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes_db";
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "note_title";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_REMINDER = "reminder";
    private static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + "TEXT"
                    + COLUMN_NOTE + " TEXT,"
                    + COLUMN_REMINDER + "BOOLEAN"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    void WriteToDB(String noteTitle, String note, boolean hasReminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, noteTitle);
        values.put(COLUMN_NOTE, note);
        values.put(COLUMN_REMINDER, hasReminder);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    Note ReadFromDB(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        String noteTitle = null, note = null, time = null;
        boolean hasReminder = false;
        Note noteData;

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_NOTE, COLUMN_REMINDER, COLUMN_TIMESTAMP},
                COLUMN_TITLE + "=?",
                new String[]{title}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            noteTitle = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            note = cursor.getString(cursor.getColumnIndex(COLUMN_NOTE));
            time = cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP));
            hasReminder = cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER)) != 0;
            cursor.close();
        }
        db.close();

        if (noteTitle != null) {
            noteData = new Note(noteTitle, note, hasReminder);
            noteData.setmCreationDate(time);

            return noteData;
        }
        return null;
    }

    NotesHandler ReadAllFromDB() {
        NotesHandler notes = new NotesHandler();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note("", "", cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER)) != 0);
                note.setmNoteTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                note.setmNoteContent(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE)));
                note.setmCreationDate(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                notes.addNoteToList(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }

    int storedNotes() {
        int count;

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        count = cursor.getCount();
        cursor.close();

        db.close();

        return count;
    }

    void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_TITLE + " = ?",
                new String[]{String.valueOf(note.getmNoteTitle())});
        db.close();
    }
}
