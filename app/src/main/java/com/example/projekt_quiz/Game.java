package com.example.projekt_quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.projekt_quiz.questions.QuestionResponse;
import com.example.projekt_quiz.questions.QuestionRepository;
import com.example.projekt_quiz.questions.QuestionService;

import java.util.concurrent.atomic.AtomicInteger;

public class Game extends AppCompatActivity {

    private QuestionRepository[] questions;
    private Button trueBtn, falseBtn;
    private AtomicInteger score;
    private int questionNumber;
    private boolean interruptedWithCall = false;
    private CountDownTimer cntDTimer;
    private TextView timeText, scoreText, questionText;
    private long timeLeftMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null) {
            score = new AtomicInteger(savedInstanceState.getInt("score"));
            questionNumber = savedInstanceState.getInt("questionNumber");
            interruptedWithCall = savedInstanceState.getBoolean("interruptedWithCall");
            timeLeftMS = savedInstanceState.getLong("timeLeftMS");
        } else {
            score = new AtomicInteger();
            questionNumber = 0;
            timeLeftMS = 30000;
        }
        if (!interruptedWithCall) {
            getQuestions();
        } else {
            resumeGame();
        }
    }

    private void getQuestions() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionService service = retrofit.create(QuestionService.class);
        Call<QuestionResponse> call = service.getQuestions();
        call.enqueue(new Callback<QuestionResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuestionResponse> call, @NonNull Response<QuestionResponse> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                QuestionResponse apiResponse = response.body();
                assert apiResponse != null;
                questions = apiResponse.getResults();
                startGame();
            }

            @Override
            public void onFailure(@NonNull Call<QuestionResponse> call, @NonNull Throwable t) {
                Log.e("Error", "Failed to get the questions from API", t);
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void startGame() {
        timeText = findViewById(R.id.timeLeft);
        scoreText = findViewById(R.id.currentScore);
        questionText = findViewById(R.id.questionText);
        trueBtn = findViewById(R.id.trueButton);
        falseBtn = findViewById(R.id.falseButton);
        displayQuestion();

        scoreText.setText(String.format("Points: %d", score.get()));
        btnListeners();
        cntDTimer = new CountDownTimer(timeLeftMS, 1000) {

            @Override
            public void onTick(long untilEndMS) {
                timeLeftMS = untilEndMS;
                timeText.setText(String.format("Time: %d", timeLeftMS / 1000));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Game.this, GameEnd.class);
                intent.putExtra("score", score.get());
                startActivity(intent);
            }
        }.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle saveState) {
        saveState.putInt("score", score.get());
        saveState.putInt("questionNumber", questionNumber);
        saveState.putBoolean("interruptedWithCall", interruptedWithCall);
        saveState.putLong("timeLeftMS", timeLeftMS);
        super.onSaveInstanceState(saveState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        interruptedWithCall = true;
        if (cntDTimer != null) {
            cntDTimer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (interruptedWithCall) {
            interruptedWithCall = false;
            resumeGame();
        }
    }
    
    private void resumeGame() {
        displayQuestion();
        cntDTimer = new CountDownTimer(timeLeftMS, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long untilEndMS) {
                timeLeftMS = untilEndMS;
                timeText.setText(String.format("Time: %d", untilEndMS / 1000));
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(Game.this, GameEnd.class);
                intent.putExtra("score", score.get());
                startActivity(intent);
            }
        }.start();
    }

    private void displayQuestion() {
        if (questionNumber < questions.length) {
            QuestionRepository question = questions[questionNumber];
            String decodedQuestion = Html.fromHtml(question.getQuestion(), Html.FROM_HTML_MODE_LEGACY).toString();
            questionText.setText(decodedQuestion);
        }
    }
    private void checkAnswer(String chosenAnswer) {
        if (questionNumber < questions.length) {
            QuestionRepository question = questions[questionNumber];

            if (question.getCorrectAnswer().equals(chosenAnswer)) {
                score.getAndAdd(20);
            }
            else{
                if(score.get() != 0){
                score.getAndAdd(-5);
                }
            }
            scoreText.setText(String.format("Points %d", score.get()));
            questionNumber++;
            displayQuestion();
        }
    }
    private void btnListeners() {
        trueBtn.setOnClickListener(view -> checkAnswer("True"));
        falseBtn.setOnClickListener(view -> checkAnswer("False"));
    }
    
}