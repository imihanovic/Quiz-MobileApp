package com.example.projekt_quiz.questions;

import retrofit2.Call;
import retrofit2.http.GET;
public interface QuestionService {
    @GET("api.php?amount=30&category=9&difficulty=easy&type=boolean")
    Call<QuestionResponse> getQuestions();
}
