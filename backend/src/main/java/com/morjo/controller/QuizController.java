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
import com.morjo.model.dto.QuizSubmit;
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

    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuiz(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null || !userService.checkUser(userId)) {
            userId = 0L;
        }

        Quiz quiz = quizService.getQuizRandom(userId);

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
    public ResponseEntity<?> postCreateQuiz(@RequestBody Quiz quiz, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 상태가 아닙니다");
        }

        boolean check = userService.checkUser(userId);

        if (!check) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사이트 회원이 아닙니다");
        }

        quiz.setCreatedUserId(userId);

        long quizId = quizService.createQuiz(quiz);

        if (quizId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("본문, 선택지 또는 정답 값에 오류가 있습니다");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(quizId);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> postQuizSubmit(@RequestBody QuizSubmit quizSubmit, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 상태가 아닙니다");
        }

        boolean check = userService.checkUser(userId);

        if (!check) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사이트 회원이 아닙니다");
        }

        boolean isSolved = quizService.isSolved(quizSubmit.getQuizId(), userId);

        if (isSolved) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 푼 문제입니다");
        }

        quizSubmit.setUserId(userId);

        int result = quizService.submitQuizResult(quizSubmit);

        if (result != 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효한 선택지가 아닙니다");
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
