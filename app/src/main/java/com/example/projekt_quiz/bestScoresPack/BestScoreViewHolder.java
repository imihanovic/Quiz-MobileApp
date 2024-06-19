package com.example.projekt_quiz.bestScoresPack;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.projekt_quiz.R;

public class BestScoreViewHolder extends RecyclerView.ViewHolder {

    private final TextView rank;
    private final TextView playerName;
    private final TextView score;

    public BestScoreViewHolder(View view) {
        super(view);

        rank = view.findViewById(R.id.rank);
        playerName = view.findViewById(R.id.playerName);
        score = view.findViewById(R.id.score);
    }

    public TextView getRank() {return rank;}
    public TextView getPlayerName() {return playerName;}

    public TextView getScore() {return score;}
}

