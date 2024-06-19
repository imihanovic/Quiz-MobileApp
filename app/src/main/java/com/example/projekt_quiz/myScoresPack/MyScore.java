package com.example.projekt_quiz.myScoresPack;

public class MyScore implements Comparable<MyScore> {

    private final String scoreDate;
    private final int score;

    public MyScore(String scoreDate, int score) {
        this.scoreDate = scoreDate;
        this.score = score;
    }

    public String getScoreDateFromClass() {
        return scoreDate;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(MyScore other) { return Long.compare(Long.parseLong(this.scoreDate), Long.parseLong(other.scoreDate)); }
}
