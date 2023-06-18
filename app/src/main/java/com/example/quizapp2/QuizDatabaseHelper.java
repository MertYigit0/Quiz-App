package com.example.quizapp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;

    // Questions table
    private static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
            "Questions (question_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "question_text TEXT, option1 TEXT, option2 TEXT, option3 TEXT, option4 TEXT)";

    // Answers table
    private static final String CREATE_ANSWERS_TABLE = "CREATE TABLE " +
            "Answers (answer_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "question_id INTEGER, answer TEXT, FOREIGN KEY (question_id) REFERENCES Questions(question_id))";


    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_ANSWERS_TABLE);

        QuizQuestions quizQuestions = new QuizQuestions(db);
        quizQuestions.insertQuestions();

        Answers answers = new Answers(db);
        answers.insertAnswers();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Questions");
        db.execSQL("DROP TABLE IF EXISTS Answers");
        onCreate(db);
    }
}
