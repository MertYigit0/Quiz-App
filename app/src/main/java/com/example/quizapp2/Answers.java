package com.example.quizapp2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Answers {
    private SQLiteDatabase db;

    public Answers(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertAnswers() {
        String[] answers = {
                "Paris",
                "Leonardo da Vinci",
                "Jupiter",
                "Au",
                "William Shakespeare",
                "Nile",
                "Pacific Ocean",
                "Yen",
                "J.K. Rowling",
                "China",
                "Alexander Graham Bell",
                "Asia",
                "Mars",
                "Alexander Fleming",
                "O",
                "Kangaroo",
                "Vincent van Gogh",
                "Mount Everest",
                "Egypt",
                "Jane Austen",
                "Ag",
                "Rose",
                "George Orwell",
                "Elephant",
                "Avengers: Endgame"
        };

        for (int i = 0; i < answers.length; i++) {
            ContentValues values = new ContentValues();
            values.put("question_id", i + 1);
            values.put("answer", answers[i]);
            db.insert("Answers", null, values);
        }
    }
}
