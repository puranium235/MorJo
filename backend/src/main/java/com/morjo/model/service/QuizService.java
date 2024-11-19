package com.morjo.model.service;

import com.morjo.model.dao.QuizDao;
import com.morjo.model.dto.Quiz;
import com.morjo.model.dto.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizService {
    private final QuizDao quizDao;

    public Quiz getQuizRandom() {
        return quizDao.selectQuizRandom();
    }

    public Quiz getQuizById(long quizId) {
        return quizDao.selectQuizById(quizId);
    }

    public QuizResult getQuizResultById(long quizId) {
        Quiz quiz = quizDao.selectQuizById(quizId);

        if (quiz == null) {
            return null;
        }

        return quizDao.selectQuizResultById(quizId);
    }
}
