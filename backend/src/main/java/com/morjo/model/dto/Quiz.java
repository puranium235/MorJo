package com.morjo.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Quiz {
    private long quizId;
    private String content;
    private List<String> options = new ArrayList<>();
    private long createdUserId;
    private Timestamp createdAt;

    public void setOptions(String option) {
        options.add(option);
    }
}
