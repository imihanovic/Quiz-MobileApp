package com.example.projekt_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.projekt_quiz.bestScoresPack.BestScore;
import com.example.projekt_quiz.bestScoresPack.BestScoreAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BestScores extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ArrayList<BestScore> scoresDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_scores);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("all_scores");

        BestScoreAdapter customAdapter = new BestScoreAdapter(scoresDB);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ArrayList<BestScore> bestScores = new ArrayList<>();

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String userId = child.getKey();
                        int score = child.getValue(Integer.class);
                        bestScores.add(new BestScore(userId, score));
                    }

                    bestScores.sort(Collections.reverseOrder());
                    scoresDB.clear();
                    scoresDB.addAll(bestScores.subList(0, Math.min(10, bestScores.size())));
                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed: " + databaseError.getCode());
            }
        });

        TextView back = findViewById(R.id.back);
        back.setOnClickListener(view -> finish());
    }
}