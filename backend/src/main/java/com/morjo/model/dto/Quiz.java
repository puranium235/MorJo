package com.morjo.model.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Quiz {
    private long quizId;
    private String content;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answer;
    private long createdUserId;
    private Timestamp createdAt;
}
