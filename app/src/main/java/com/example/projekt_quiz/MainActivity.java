package com.example.projekt_quiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView logout;
    FirebaseAuth mAuth;
    Button newGame, bestScores, myScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, Login.class));
        });

        newGame = findViewById(R.id.newGameBtn);
        newGame.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, Game.class)));

        bestScores = findViewById(R.id.bestScoresBtn);
        bestScores.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, BestScores.class)));

        myScores = findViewById(R.id.myScoresBtn);
        myScores.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, MyScores.class)));
    }
}