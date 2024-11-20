package com.morjo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morjo.model.dto.Quiz;
import com.morjo.model.dto.QuizResult;
import com.morjo.model.service.QuizService;
import com.morjo.model.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    private final UserService userService;

    // !TODO 유저정보를 받아서 안 푼 문제만 주게 수정필요
    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuiz() {
        Quiz quiz = quizService.getQuizRandom();

        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("퀴즈가 없습니다");
        }

        return ResponseEntity.status(HttpStatus.OK).body(quiz);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getSpecificQuiz(@PathVariable("quizId") int quizId) {
        Quiz quiz = quizService.getQuizById(quizId);

        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 번호의 퀴즈를 찾을 수 없습니다");
        }

        return ResponseEntity.status(HttpStatus.OK).body(quiz);
    }

    @GetMapping("/{quizId}/result")
    public ResponseEntity<?> getQuizResult(@PathVariable("quizId") int quizId) {
        QuizResult result = quizService.getQuizResultById(quizId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 번호의 퀴즈를 찾을 수 없습니다");
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody Quiz quiz, HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        
        if (userIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        
        long userId = (long) userIdObj;
        boolean checkUser = userService.checkUser(userId);
        
        if (!checkUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 사용자입니다.");
        }
        
        quiz.setCreatedUserId(userId);
        
        long quizId = quizService.createQuiz(quiz);

        if (quizId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본문, 선택지 또는 정답 값에 오류가 있습니다.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(quizId);
    }

}
