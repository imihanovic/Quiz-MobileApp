package com.example.projekt_quiz.questions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionRepository {
    @SerializedName("type")
    @Expose
    private final String type;
    @SerializedName("difficulty")
    @Expose
    private final String difficulty;
    @SerializedName("category")
    @Expose
    private final String category;
    @SerializedName("question")
    @Expose
    private final String question;
    @SerializedName("correct_answer")
    @Expose
    private final String correctAnswer;
    @SerializedName("incorrect_answers")
    @Expose
    private final String[] incorrectAnswers;

    public QuestionRepository(String type, String difficulty, String category, String question, String correctAnswer, String[] incorrectAnswers) {
        this.type = type;
        this.difficulty = difficulty;
        this.category = category;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }
    public String getQuestion() {
        return question;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
