package com.example.projekt_quiz.bestScoresPack;

public class BestScore implements Comparable<BestScore> {

    private final String playerName;
    private final int score;

    public BestScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(BestScore other) { return Integer.compare(this.score, other.score); }
}
