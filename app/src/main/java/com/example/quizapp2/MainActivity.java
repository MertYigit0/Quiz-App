package com.example.quizapp2;

import android.annotation.SuppressLint;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.media.AudioManager;
import android.media.SoundPool;
import com.example.quizapp2.QuizDatabaseHelper;
import com.example.quizapp2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;
    private Button button1, button2, button3 ,button4;
    private ImageButton imageButton;

    private int currentQuestionIndex = 0; // Mevcut sorunun indeksi
    private int score = 0;
    private QuizDatabaseHelper dbHelper;

    private SoundPool soundPool;
    private int soundID , soundID2;
    public      int questionNumber = 1;

    private ProgressBar progressBar ;

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
        imageButton = findViewById(R.id.imageButton);
        progressBar = findViewById(R.id.progressBar2);

        // Veritabanı bağlantısı oluştur
        dbHelper = new QuizDatabaseHelper(this);

        button1.setOnClickListener(view -> checkAnswer(1));
        button2.setOnClickListener(view -> checkAnswer(2));
        button3.setOnClickListener(view -> checkAnswer(3));
        button4.setOnClickListener(view -> checkAnswer(4));
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideIncorrectOptions();
                imageButton.setEnabled(false);
            }
        });

        // İlk soruyu ekrana göster
        showQuestion(currentQuestionIndex);


        // SoundPool oluştur
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        // Ses dosyasını yükle
        soundID = soundPool.load(this, R.raw.correctansweraound, 1);
        soundID2 = soundPool.load(this, R.raw.wronganswersound, 1);
        //show question number
        String questionCountText = getString(R.string.question_number, questionNumber);
        setTitle(questionCountText);

    }

    private void checkAnswer(int selectedOption) {
        // Veritabanı
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Doğru cevabı al
        String[] columns = {"answer"};
        Cursor cursor = db.query("Answers", columns, null, null, null, null, null);
        if (cursor.moveToPosition(currentQuestionIndex)) {
            @SuppressLint("Range")  String correctAnswer = cursor.getString(cursor.getColumnIndex("answer"));

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

                //ses efekti
                soundPool.play(soundID2, 1.0f, 1.0f, 1, 0, 1.0f);

                // Doğru olan seçeneği yeşil renge dönüştür
                if (correctButton != null) {
                    correctButton.setBackgroundColor(Color.GREEN);
                }
            } else {
                //ses efekti
                soundPool.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f);
                // Doğru cevap
                selectedButton.setBackgroundColor(Color.GREEN);

                // Puanı artır
                score += 10;
                textView2.setText("SCORE : " + String.valueOf(score));
            }
        }

        // Bir sonraki soruyu göster
        currentQuestionIndex++;
        new Handler().postDelayed(() -> {

            //soru numarasini arttir
            questionNumber += 1;
            String questionCountText = getString(R.string.question_number, questionNumber);
            setTitle(questionCountText);

            //bar dolumu
            progressBar.setProgress(questionNumber);

            showQuestion(currentQuestionIndex);
            resetButtonColors();
        }, 1500);
        // Tüm butonların görünürlüğünü yeniden ayarla


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
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        button4.setVisibility(View.VISIBLE);
        /// Veritabanı
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //// Soruları ve seçenekleri al
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

            textView2.setText("SCORE : "+String.valueOf(score));
        }

        // Cursor ve veritabanı kapatma
        cursor.close();
        db.close();
    }

    String  buttonColor = "#9C27B0";
    private void resetButtonColors() {
        button1.setBackgroundColor(Color.parseColor(buttonColor));
        button2.setBackgroundColor(Color.parseColor(buttonColor));
        button3.setBackgroundColor(Color.parseColor(buttonColor));
        button4.setBackgroundColor(Color.parseColor(buttonColor));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Veritabanı bağlantısını kapat
        if (dbHelper != null) {
            dbHelper.close();
        }

        soundPool.release();
        soundPool = null;

    }




    private void hideIncorrectOptions() {
        // Veritabanı
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        imageButton.setImageResource(R.drawable.icon50_2);
        // Doğru cevabı al
        String[] columns = {"answer"};
        Cursor cursor = db.query("Answers", columns, null, null, null, null, null);
        if (cursor.moveToPosition(currentQuestionIndex)) {
            @SuppressLint("Range") String correctAnswer = cursor.getString(cursor.getColumnIndex("answer"));

            // Diğer seçenekleri al
            String[] options = {button1.getText().toString(), button2.getText().toString(), button3.getText().toString(), button4.getText().toString()};

            // Doğru cevabın indeksini bul
            int correctOptionIndex = -1;
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(correctAnswer)) {
                    correctOptionIndex = i;
                    break;
                }
            }

            // Yanlış seçenekleri gizle
            List<Integer> hiddenOptionIndices = new ArrayList<>();
            Random random = new Random();

            while (hiddenOptionIndices.size() < 2) {
                int randomIndex = random.nextInt(options.length);

                if (randomIndex != correctOptionIndex && !hiddenOptionIndices.contains(randomIndex)) {
                    hiddenOptionIndices.add(randomIndex);
                    hideOptionByIndex(randomIndex);
                }
            }
        }

        // Cursor ve veritabanı kapatma
        cursor.close();
        db.close();
    }

    private void hideOptionByIndex(int index) {
        switch (index) {
            case 0:
                button1.setVisibility(View.INVISIBLE);
                break;
            case 1:
                button2.setVisibility(View.INVISIBLE);
                break;
            case 2:
                button3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                button4.setVisibility(View.INVISIBLE);
                break;
        }
    }




}