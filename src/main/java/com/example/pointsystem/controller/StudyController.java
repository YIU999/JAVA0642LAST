package com.example.pointsystem.controller;

import com.example.pointsystem.service.StudyService;
import org.springframework.http.HttpStatus; // HttpStatus 임포트 추가
import org.springframework.http.ResponseEntity; // ResponseEntity 임포트 추가
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/study")
@CrossOrigin
public class StudyController {

    private final StudyService studyService;


    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }


    @PostMapping("/start")
    public ResponseEntity<String> startStudy(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Username is required", HttpStatus.BAD_REQUEST);
        }
        try {
            studyService.startStudySession(username);
            return new ResponseEntity<>("Study session started for " + username, HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>("Failed to start study session: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/end")
    public ResponseEntity<String> endStudy(@RequestBody Map<String, String> body) {
        String username = body.get("username");


        if (username == null || username.isEmpty()) {
            return new ResponseEntity<>("Username is required", HttpStatus.BAD_REQUEST);
        }

        try {
            studyService.endStudySession(username);

            return new ResponseEntity<>("Study session ended for " + username + " and points recorded.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to end study session: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}