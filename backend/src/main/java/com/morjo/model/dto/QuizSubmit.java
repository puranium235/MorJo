package com.morjo.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuizSubmit {
    private long quizId;
    private long userId;
    private int userAnswer;
    private Boolean isCommonSense;
}
