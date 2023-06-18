package com.example.quizapp2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizapp2.QuizDatabaseHelper;
import com.example.quizapp2.R;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private int currentQuestionIndex = 0; // Mevcut sorunun indeksi

    private int score = 0;

    private QuizDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML bileşenlerini Java koduna bağlayın
        textView2 = findViewById(R.id.textView);
        textView = findViewById(R.id.textView2);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        // Veritabanı bağlantısı oluştur
        dbHelper = new QuizDatabaseHelper(this);

        button1.setOnClickListener(view -> checkAnswer(1));
        button2.setOnClickListener(view -> checkAnswer(2));
        button3.setOnClickListener(view -> checkAnswer(3));
        button4.setOnClickListener(view -> checkAnswer(4));

        showQuestion(currentQuestionIndex); // İlk soruyu ekrana göster
    }

    private void checkAnswer(int selectedOption) {
        // Veritabanı
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Doğru cevabı al
        String[] columns = {"answer"};
        Cursor cursor = db.query("Answers", columns, null, null, null, null, null);
        if (cursor.moveToPosition(currentQuestionIndex)) {
            @SuppressLint("Range") String correctAnswer = cursor.getString(cursor.getColumnIndex("answer"));

            // Seçilen seçeneği kontrol et
            String selectedOptionText = "";
            Button selectedButton = null;
            Button correctButton = null;
            switch (selectedOption) {
                case 1:
                    selectedOptionText = button1.getText().toString();
                    selectedButton = button1;
                    correctButton = getCorrectButton(correctAnswer);
                    break;
                case 2:
                    selectedOptionText = button2.getText().toString();
                    selectedButton = button2;
                    correctButton = getCorrectButton(correctAnswer);
                    break;
                case 3:
                    selectedOptionText = button3.getText().toString();
                    selectedButton = button3;
                    correctButton = getCorrectButton(correctAnswer);
                    break;
                case 4:
                    selectedOptionText = button4.getText().toString();
                    selectedButton = button4;
                    correctButton = getCorrectButton(correctAnswer);
                    break;
            }

            // Yanlış cevap
            if (!correctAnswer.equals(selectedOptionText)) {
                selectedButton.setBackgroundColor(Color.RED);

                // Doğru olan seçeneği yeşil renge dönüştür
                if (correctButton != null) {
                    correctButton.setBackgroundColor(Color.GREEN);
                }
            } else {
                // Doğru cevap
                selectedButton.setBackgroundColor(Color.GREEN);

                // Puanı artır
                score += 10;
                textView2.setText(String.valueOf(score));
            }
        }

        // Bir sonraki soruyu göster
        currentQuestionIndex++;
        new Handler().postDelayed(() -> {
            showQuestion(currentQuestionIndex);
            resetButtonColors();
        }, 1000);

        // Cursor ve veritabanı kapatma
        cursor.close();
        db.close();
    }

    private Button getCorrectButton(String correctAnswer) {
        if (correctAnswer.equals(button1.getText().toString())) {
            return button1;
        } else if (correctAnswer.equals(button2.getText().toString())) {
            return button2;
        } else if (correctAnswer.equals(button3.getText().toString())) {
            return button3;
        } else if (correctAnswer.equals(button4.getText().toString())) {
            return button4;
        }
        return null;
    }

    private void showQuestion(int questionIndex) {
        // Veritabanı
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        /// Soruları ve seçenekleri al
        String[] columns = {"question_text", "option1", "option2", "option3", "option4"};
        Cursor cursor = db.query("Questions", columns, null, null, null, null, null);
        if (cursor.moveToPosition(questionIndex)) {
            String questionText = cursor.getString(0);
            String option1 = cursor.getString(1);
            String option2 = cursor.getString(2);
            String option3 = cursor.getString(3);
            String option4 = cursor.getString(4);

            // Soruyu ve seçenekleri ekrana yazdır
            textView.setText(questionText);
            button1.setText(option1);
            button2.setText(option2);
            button3.setText(option3);
            button4.setText(option4);

            // Puanı güncelle (yalnızca doğru cevaplarda)

            textView2.setText(String.valueOf(score));
        }

        // Cursor ve veritabanı kapatma
        cursor.close();
        db.close();
    }

    private void resetButtonColors() {
        button1.setBackgroundColor(Color.parseColor("#A020F0"));
        button2.setBackgroundColor(Color.parseColor("#A020F0"));
        button3.setBackgroundColor(Color.parseColor("#A020F0"));
        button4.setBackgroundColor(Color.parseColor("#A020F0"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Veritabanı bağlantısını kapat
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
