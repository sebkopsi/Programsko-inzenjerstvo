package com.cookingflamingoz.backend.controller.lecture;

import com.cookingflamingoz.backend.model.EnrolledLecture;
import com.cookingflamingoz.backend.model.EnrolledLecture.EnrolledLectureId;
import com.cookingflamingoz.backend.model.Lecture;
import com.cookingflamingoz.backend.repository.EnrolledLectureRepository;
import com.cookingflamingoz.backend.repository.LectureRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> submitQuiz(@PathVariable Integer lectureId,
                                        @RequestBody Map<String, String> body) {


        Integer userId = (Integer) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

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

        // int totalQuestions = lecture.getQuizjson().questions.size();
        int totalQuestions = (int) lecture.getQuizjson().questions.stream()
            .filter(q -> q.type != null && !q.type.equalsIgnoreCase("text"))
            .count();
        int correctAnswers = 0;


        for (var option : lecture.getQuizjson().options) {
            if (!option.correct) continue;

            if (answersJson.contains("\"" + option.questionId + "\":" + option.id)) {
                correctAnswers++;
            }
        }

        int score = (int) ((double) correctAnswers / totalQuestions * 100);

        enrolled.setQuizanswers(answersJson);

        if (score >= lecture.getMinScore()) {
            enrolled.setCompletionPercentage(100);
        } else {
            enrolled.setCompletionPercentage(score);
        }

        enrolledLectureRepository.save(enrolled);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "lectureId", lectureId,
                "userId", userId,
                "completionPercentage", enrolled.getCompletionPercentage()
        ));
    }
}