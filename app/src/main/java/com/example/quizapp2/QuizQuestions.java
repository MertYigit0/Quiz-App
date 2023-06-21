package com.example.quizapp2;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizQuestions {
    private SQLiteDatabase db;

    public QuizQuestions(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertQuestions() {
        String[][] questions = {
                {"What is the capital of France?", "London", "Paris", "Madrid", "Berlin"},
                {"Who painted the Mona Lisa?", "Michelangelo", "Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci"},
                {"What is the largest planet in our solar system?", "Mars", "Neptune", "Saturn", "Jupiter"},
                {"What is the chemical symbol for gold?", "Cu", "Au", "Ag", "Fe"},
                {"Who wrote the play Romeo and Juliet?", "Charles Dickens", "Jane Austen", "George Orwell", "William Shakespeare"},
                {"What is the longest river in the world?", "Yangtze", "Mississippi", "Amazon", "Nile"},
                {"What is the largest ocean in the world?", "Arctic Ocean", "Indian Ocean", "Atlantic Ocean", "Pacific Ocean"},
                {"What is the currency of Japan?", "Pound", "Dollar", "Euro", "Yen"},
                {"Who is the author of the Harry Potter book series?", "George R.R. Martin", "Dan Brown", "Stephen King", "J.K. Rowling"},
                {"Which country is famous for the Great Wall?", "Egypt", "India", "China", "Italy"},
                {"Who invented the telephone?", "Albert Einstein", "Nikola Tesla", "Thomas Edison", "Alexander Graham Bell"},
                {"What is the largest continent in the world?", "Europe", "North America", "Africa", "Asia"},
                {"Which planet is known as the Red Planet?", "Uranus", "Mercury", "Venus", "Mars"},
                {"Who discovered penicillin?", "Albert Einstein", "Isaac Newton", "Marie Curie", "Alexander Fleming"},
                {"What is the chemical symbol for oxygen?", "N", "H", "C", "O"},
                {"What is the national animal of Australia?", "Platypus", "Koala", "Emu", "Kangaroo"},
                {"Who painted the Starry Night?", "Claude Monet", "Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh"},
                {"What is the tallest mountain in the world?", "Matterhorn", "Kilimanjaro", "K2", "Mount Everest"},
                {"Which country is famous for the pyramids?", "China", "Mexico", "Greece", "Egypt"},
                {"Who is the author of the novel Pride and Prejudice?", "Virginia Woolf", "Charlotte Bronte", "Emily Bronte", "Jane Austen"},
                {"What is the chemical symbol for silver?", "Pt", "Cu", "Au", "Ag"},
                {"What is the national flower of England?", "Lily", "Lotus", "Tulip", "Rose"},
                {"Who is the author of the novel 1984?", "Aldous Huxley", "Ernest Hemingway", "F. Scott Fitzgerald", "George Orwell"},
                {"What is the largest land animal?", "Rhinoceros", "Hippopotamus", "Giraffe", "Elephant"},
                {"What is the highest-grossing film of all time?", "Star Wars: The Force Awakens", "Titanic", "Avatar", "Avengers: Endgame"}
        };

        shuffleOptions(questions); // Soru ve cevapların yerlerini karıştırma

        for (int i = 0; i < questions.length; i++) {
            String[] question = questions[i];

            ContentValues values = new ContentValues();
            values.put("question_text", question[0]);

            // İlk indexteki cevabı sabit olarak yerleştirme
            String firstOption = question[1];
            question = Arrays.copyOfRange(question, 2, question.length);
            shuffleArray(question);
            String[] options = new String[question.length + 1];
            options[0] = firstOption;
            System.arraycopy(question, 0, options, 1, question.length);

            values.put("option1", options[0]);
            values.put("option2", options[1]);
            values.put("option3", options[2]);
            values.put("option4", options[3]);
            db.insert("Questions", null, values);
        }
    }

    private void shuffleOptions(String[][] questions) {
        for (int i = 0; i < questions.length; i++) {
            List<String> options = Arrays.asList(questions[i]);
            Collections.shuffle(options.subList(1, options.size())); // İlk indexi sabit tutma
            questions[i] = options.toArray(new String[0]);
        }
    }

    private void shuffleArray(String[] array) {
        List<String> list = Arrays.asList(array);
        Collections.shuffle(list);
        list.toArray(array);
    }
}
