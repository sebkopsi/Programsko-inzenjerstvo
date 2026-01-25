package com.cookingflamingoz.backend.controller.lecture;

import com.cookingflamingoz.backend.model.EnrolledLecture;
import com.cookingflamingoz.backend.model.EnrolledLecture.EnrolledLectureId;
import com.cookingflamingoz.backend.model.Lecture;
import com.cookingflamingoz.backend.model.Quiz;
import com.cookingflamingoz.backend.repository.EnrolledLectureRepository;
import com.cookingflamingoz.backend.repository.LectureRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/lecture")
public class QuizController {

    private final EnrolledLectureRepository enrolledLectureRepository;
    private final LectureRepository lectureRepository;

    public QuizController(EnrolledLectureRepository enrolledLectureRepository,
        LectureRepository lectureRepository) {
        this.enrolledLectureRepository = enrolledLectureRepository;
        this.lectureRepository = lectureRepository;
    }

    @PostMapping("/{lectureId}/quiz/submit")
    public ResponseEntity<?> submitQuiz(@PathVariable Integer lectureId, @RequestBody Map<String, String> body) {

        Integer userId = (Integer)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String answersJson = body.getOrDefault("answersJson", "{}");

        EnrolledLectureId id = new EnrolledLectureId(lectureId, userId);
        EnrolledLecture enrolled = enrolledLectureRepository.findById(id)
            .orElseGet(() -> {
                EnrolledLecture e = new EnrolledLecture();
                e.setId(id);
                e.setCompletionPercentage(0);
                e.setQuizanswers("{}");
                return e;
            });

        

        Lecture lecture = lectureRepository.findById(lectureId)
            .orElseThrow(() -> new RuntimeException("Lecture not found"));

        Quiz quiz = lecture.getQuizjson();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> answersMap;

        try {
            answersMap = mapper.readValue(answersJson, Map.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "success", false,
                    "message", "Invalid answers format"
                )
            );
        }

        int totalQuestions = 0;
        int correctAnswers = 0;

        Map<Integer, List<Quiz.Option>> optionsByQuestion =
            quiz.options.stream().collect(
            Collectors.groupingBy(op -> op.questionId));

        for (Quiz.Question question : quiz.questions) {
            if ("text".equalsIgnoreCase(question.type)) {
                continue;
            }

            totalQuestions += 1;

            List<Quiz.Option> options = optionsByQuestion.getOrDefault(question.id, List.of());

            Set<String> correctOptionIds = options.stream()
                .filter(op -> op.correct)
                .map(op -> String.valueOf(op.id))
                .collect(Collectors.toSet());

            Object userAnswer = answersMap.get(String.valueOf(question.id));
            Set<String> userOptionIds = new HashSet<>();

            if (userAnswer instanceof String s) {
                userOptionIds.add(s);
            } else if (userAnswer instanceof List<?> list) {
                for (Object o : list) {
                    userOptionIds.add(String.valueOf(o));
                }
            }

            if (userOptionIds.equals(correctOptionIds)) {
                correctAnswers += 1;
            }
        }

        int score = totalQuestions == 0 ? 0 : (int) ((double) correctAnswers / totalQuestions * 100);

        enrolled.setQuizanswers(answersJson);

        if (lecture.getMinScore() != null && score >= lecture.getMinScore()) {
            enrolled.setCompletionPercentage(100);
        } else {
            enrolled.setCompletionPercentage(score);
        }

        enrolledLectureRepository.save(enrolled);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "lectureId", lectureId,
                "userId", userId,
                "completionPercentage", enrolled.getCompletionPercentage(),
                "correctAnswers", correctAnswers,
                "totalQuestions", totalQuestions
        ));
    }
}
