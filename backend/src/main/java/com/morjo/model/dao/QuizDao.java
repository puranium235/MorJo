package com.morjo.model.dao;

import com.morjo.model.dto.Quiz;
import com.morjo.model.dto.QuizResult;

public interface QuizDao {
    Quiz selectQuizRandom(long userId);
    Quiz selectQuizById(long quizId);
    QuizResult selectQuizResultById(long quizId);
    void insertQuiz(Quiz quiz);
}
