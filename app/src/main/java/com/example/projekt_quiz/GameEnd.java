package com.example.projekt_quiz;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class GameEnd extends AppCompatActivity {

    KonfettiView celebrationView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    FirebaseDatabase db;
    DatabaseReference refAll, refMy;
    int currentScore, highScore;
    ValueAnimator scoreAnimator;
    String email = Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser())).getEmail();
    String playerName;
    {
        assert email != null;
        playerName = email.substring(0, email.indexOf('@'));
    }
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        db = FirebaseDatabase.getInstance();
        refAll = db.getReference("all_scores");
        refMy = db.getReference("my_scores");
        Intent intent = getIntent();
        currentScore = intent.getIntExtra("score", 0);

        saveScore();
        getHighScore();

        Button playAgainBtn = findViewById(R.id.playAgainBtn);
        playAgainBtn.setOnClickListener(view -> startActivity(new Intent(GameEnd.this, Game.class)));

        Button bestScoresBtn = findViewById(R.id.bestScoresBtn);
        bestScoresBtn.setOnClickListener(view -> startActivity(new Intent(GameEnd.this, BestScores.class)));

        Button myScoresBtn = findViewById(R.id.myScoresBtn);
        myScoresBtn.setOnClickListener(view -> startActivity(new Intent(GameEnd.this, MyScores.class)));

        TextView mainBtn = findViewById(R.id.mainBtn);
        mainBtn.setOnClickListener(view -> startActivity(new Intent(GameEnd.this, MainActivity.class)));
    }

    private void saveScore() {
        long currentMS = System.currentTimeMillis();
        refMy.child(playerName).child(String.valueOf(currentMS)).setValue(currentScore);
    }

    @SuppressLint("DefaultLocale")
    private void getHighScore() {
        refAll.child(playerName).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                highScore = snapshot.getValue(Integer.class);
            } else {
                highScore = 0;
            }

            updateHighScore(Objects.requireNonNull(Objects.requireNonNull(mAuth.getCurrentUser()).getEmail()));

            TextView scoreText = findViewById(R.id.score);
            TextView highScoreText = findViewById(R.id.highScore);

            scoreText.setText(String.format("Score: %d", currentScore));
            highScoreText.setText(String.format("High score %d", highScore));
        }).addOnFailureListener(e -> {
            Log.d("Firebase", "Error getting data from DB", e);
            highScore = 0;
        });
    }
    private void celebrationAnimation() {
        celebrationView = findViewById(R.id.konfetiView);
        EmitterConfig emitterConfig = new Emitter(500, TimeUnit.MILLISECONDS).max(500);
        celebrationView.start(
                new PartyFactory(emitterConfig)
                        .shapes(Shape.Square.INSTANCE)
                        .spread(360)
                        .colors(new ArrayList<>(Arrays.asList(Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.CYAN)))
                        .position(0, 0, 1, 1)
                        .timeToLive(20000)
                        .fadeOutEnabled(true)
                        .build()
        );
    }
    @SuppressLint("DefaultLocale")
    private void updateHighScore(String email) {

        TextView highScoreText = findViewById(R.id.highScore);
        String playerName = email.substring(0, email.indexOf('@'));

        if (currentScore > highScore) {
            celebrationAnimation();
            if (scoreAnimator == null) {
                scoreAnimator = ValueAnimator.ofInt(highScore, currentScore);
                scoreAnimator.setDuration(1000);
                scoreAnimator.addUpdateListener(animation -> {
                    int animatedValue = (int) animation.getAnimatedValue();
                    highScoreText.setText(String.format("High score %d", animatedValue));
                });
                scoreAnimator.start();
            } else {
                scoreAnimator.setIntValues(highScore, currentScore);
                scoreAnimator.start();
            }

            highScore = currentScore;
            refAll.child(playerName).setValue(highScore);
        }
    }
}