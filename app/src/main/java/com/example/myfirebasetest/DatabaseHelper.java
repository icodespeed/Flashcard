package com.example.myfirebasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "flashcards.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_FLASHCARDS = "flashcards";

    // Column Names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_ANSWER = "answer";

    // Create Table SQL Query
    private static final String CREATE_TABLE_FLASHCARDS = "CREATE TABLE " + TABLE_FLASHCARDS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_QUESTION + " TEXT, "
            + COLUMN_ANSWER + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_FLASHCARDS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHCARDS);
        onCreate(db);
    }

    // Insert new flashcard into the database
    public long addFlashCard(String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if a flashcard with the same question already exists
        if (isFlashCardExists(question)) {
            return -1; // Return -1 if the flashcard already exists
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_ANSWER, answer);

        long id = db.insert(TABLE_FLASHCARDS, null, values);
        db.close();
        return id;
    }

    // Check if flashcard with the same question exists
    private boolean isFlashCardExists(String question) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FLASHCARDS, new String[]{COLUMN_ID},
                COLUMN_QUESTION + "=?", new String[]{question}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;  // Flashcard exists
        } else {
            cursor.close();
            return false; // Flashcard doesn't exist
        }
    }

    // Get a flashcard by ID
    public FlashCard getFlashCardById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FLASHCARDS, new String[]{COLUMN_ID, COLUMN_QUESTION, COLUMN_ANSWER},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            FlashCard flashCard = new FlashCard(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            cursor.close();
            return flashCard;
        } else {
            return null;
        }
    }

    // Get all flashcards
    public List<FlashCard> getAllFlashCards() {
        List<FlashCard> flashCardList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FLASHCARDS, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                FlashCard flashCard = new FlashCard(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                flashCardList.add(flashCard);
            }
            cursor.close();
        }
        return flashCardList;
    }

    // Update an existing flashcard
    public int updateFlashCard(int id, String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_ANSWER, answer);

        // Update the flashcard in the database
        return db.update(TABLE_FLASHCARDS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Delete a flashcard by ID
    public void deleteFlashCard(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FLASHCARDS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
