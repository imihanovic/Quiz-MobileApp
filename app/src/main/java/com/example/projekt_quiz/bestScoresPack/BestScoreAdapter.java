package com.example.projekt_quiz.bestScoresPack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projekt_quiz.R;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;

public class BestScoreAdapter extends RecyclerView.Adapter<BestScoreViewHolder> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final ArrayList<BestScore> localDataSet;

    public BestScoreAdapter(ArrayList<BestScore> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public BestScoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.best_scores_element, viewGroup, false);
        return new BestScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestScoreViewHolder viewHolder, int position) {
        BestScore item = localDataSet.get(position);
        String playerName = item.getPlayerName();
        int score = item.getScore();

        viewHolder.getRank().setText(String.valueOf(position + 1));
        viewHolder.getPlayerName().setText(playerName);
        viewHolder.getScore().setText(String.valueOf(score));

        String email = mAuth.getCurrentUser().getEmail();
        if (email.substring(0, email.indexOf('@')).equals(playerName)) {
            viewHolder.itemView.setBackgroundColor(
                    ContextCompat.getColor(viewHolder.itemView.getContext(), R.color.gold)
            );
        }
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
