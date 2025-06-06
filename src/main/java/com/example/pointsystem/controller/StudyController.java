package com.example.pointsystem.controller;

import com.example.pointsystem.service.StudyService;
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

    @PostMapping("/stop")
    public void stop(@RequestBody Map<String, String> body) {
        studyService.recordStudy(
                body.get("username"),
                body.get("category"),
                body.get("startTime"),
                body.get("endTime")
        );
    }
}
