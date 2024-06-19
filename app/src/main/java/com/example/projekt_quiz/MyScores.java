package com.example.projekt_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt_quiz.myScoresPack.MyScore;
import com.example.projekt_quiz.myScoresPack.MyScoresAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Comparator;

public class MyScores extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ArrayList<MyScore> scoresDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scores);

        mAuth = FirebaseAuth.getInstance();

        String email = mAuth.getCurrentUser().getEmail();
        String username = email.substring(0, email.indexOf('@'));

        mDatabase = FirebaseDatabase.getInstance().getReference("my_scores/" + username);

        MyScoresAdapter customAdapter = new MyScoresAdapter(scoresDB);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    ArrayList<MyScore> myScores = new ArrayList<>();

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String dateS = child.getKey();
                        int score = child.getValue(Integer.class);
                        myScores.add(new MyScore(dateS, score));
                    }
                    myScores.sort(Comparator.comparing(MyScore::getScore).reversed());
                    scoresDB.clear();
                    scoresDB.addAll(myScores);

                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyScores.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        TextView back = findViewById(R.id.back);
        back.setOnClickListener(view -> finish());
    }
}