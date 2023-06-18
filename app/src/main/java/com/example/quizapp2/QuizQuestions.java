package com.example.quizapp2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class QuizQuestions {
    private SQLiteDatabase db;

    public QuizQuestions(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertQuestions() {
        String[][] questions = {
                {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid"},
                {"Who painted the Mona Lisa?", "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo"},
                {"What is the largest planet in our solar system?", "Jupiter", "Saturn", "Neptune", "Mars"},
                {"What is the chemical symbol for gold?", "Au", "Ag", "Fe", "Cu"},
                {"Who wrote the play Romeo and Juliet?", "William Shakespeare", "George Orwell", "Jane Austen", "Charles Dickens"},
                {"What is the longest river in the world?", "Nile", "Amazon", "Mississippi", "Yangtze"},
                {"What is the largest ocean in the world?", "Pacific Ocean", "Atlantic Ocean", "Indian Ocean", "Arctic Ocean"},
                {"What is the currency of Japan?", "Yen", "Euro", "Dollar", "Pound"},
                {"Who is the author of the Harry Potter book series?", "J.K. Rowling", "Stephen King", "Dan Brown", "George R.R. Martin"},
                {"Which country is famous for the Great Wall?", "China", "Italy", "India", "Egypt"},
                {"Who invented the telephone?", "Alexander Graham Bell", "Thomas Edison", "Nikola Tesla", "Albert Einstein"},
                {"What is the largest continent in the world?", "Asia", "Africa", "North America", "Europe"},
                {"Which planet is known as the Red Planet?", "Mars", "Venus", "Mercury", "Uranus"},
                {"Who discovered penicillin?", "Alexander Fleming", "Marie Curie", "Isaac Newton", "Albert Einstein"},
                {"What is the chemical symbol for oxygen?", "O", "C", "H", "N"},
                {"What is the national animal of Australia?", "Kangaroo", "Koala", "Emu", "Platypus"},
                {"Who painted the Starry Night?", "Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"},
                {"What is the tallest mountain in the world?", "Mount Everest", "K2", "Kilimanjaro", "Matterhorn"},
                {"Which country is famous for the pyramids?", "Egypt", "Greece", "Mexico", "China"},
                {"Who is the author of the novel Pride and Prejudice?", "Jane Austen", "Emily Bronte", "Charlotte Bronte", "Virginia Woolf"},
                {"What is the chemical symbol for silver?", "Ag", "Au", "Cu", "Pt"},
                {"What is the national flower of England?", "Rose", "Tulip", "Lotus", "Lily"},
                {"Who is the author of the novel 1984?", "George Orwell", "F. Scott Fitzgerald", "Ernest Hemingway", "Aldous Huxley"},
                {"What is the largest land animal?", "Elephant", "Giraffe", "Hippopotamus", "Rhinoceros"},
                {"What is the highest-grossing film of all time?", "Avengers: Endgame", "Avatar", "Titanic", "Star Wars: The Force Awakens"}
        };

        for (int i = 0; i < questions.length; i++) {
            ContentValues values = new ContentValues();
            values.put("question_text", questions[i][0]);
            values.put("option1", questions[i][1]);
            values.put("option2", questions[i][2]);
            values.put("option3", questions[i][3]);
            values.put("option4", questions[i][4]);
            db.insert("Questions", null, values);
        }
    }
}
