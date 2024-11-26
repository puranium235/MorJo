package com.morjo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizResult {
    private long quizId;
    private int answer;
    private List<Integer> userAnswers = new ArrayList<>();
    private int isCommonSense;
    private int notCommonSense;

    public void setUserAnswers(int count) {
        userAnswers.add(count);
    }
}
