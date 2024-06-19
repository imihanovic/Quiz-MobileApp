package com.example.projekt_quiz.myScoresPack;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projekt_quiz.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyScoresAdapter extends RecyclerView.Adapter<MyScoresViewHolder> {

    private final ArrayList<MyScore> localDataSet;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public MyScoresAdapter(ArrayList<MyScore> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public MyScoresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_scores_element, viewGroup, false);

        return new MyScoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyScoresViewHolder viewHolder, int position) {
        MyScore item = localDataSet.get(position);

        String scoreDate = item.getScoreDateFromClass();
        Date date = new Date(Long.parseLong(scoreDate));
        String scoreDateSdf = sdf.format(date);
        int score = item.getScore();

        viewHolder.getRank().setText(String.valueOf(position + 1));
        viewHolder.getScoreDate().setText(scoreDateSdf);
        viewHolder.getScore().setText(String.valueOf(score));
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

