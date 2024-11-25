package com.morjo.model.dao;

import com.morjo.model.dto.Quiz;
import com.morjo.model.dto.QuizResult;
import com.morjo.model.dto.QuizSubmit;

public interface QuizDao {
    Quiz selectQuizRandom(long userId);
    Quiz selectQuizById(long quizId);
    QuizResult selectQuizResultById(long quizId);
    void insertQuiz(Quiz quiz);
    int insertQuizSubmit(QuizSubmit quizSubmit);
    int selectQuizUserById(long quizId, long userId);
}
