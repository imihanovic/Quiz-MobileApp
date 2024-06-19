package com.example.projekt_quiz.questions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class QuestionResponse {
    @SerializedName("results")
    @Expose
    private QuestionRepository[] results;
    public QuestionRepository[] getResults() {
        return results;
    }
}
