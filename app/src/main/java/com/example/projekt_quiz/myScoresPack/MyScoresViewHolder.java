package com.example.projekt_quiz.myScoresPack;

import android.view.View;
import android.widget.TextView;
import com.example.projekt_quiz.R;
import androidx.recyclerview.widget.RecyclerView;

public class MyScoresViewHolder extends RecyclerView.ViewHolder {

    private final TextView rank;
    private final TextView scoreDate;
    private final TextView score;

    public MyScoresViewHolder(View view) {
        super(view);

        rank = view.findViewById(R.id.rank);
        scoreDate = view.findViewById(R.id.date);
        score = view.findViewById(R.id.score);
    }

    public TextView getRank() { return rank; }
    public TextView getScoreDate() { return scoreDate; }
    public TextView getScore() {
        return score;
    }
}

