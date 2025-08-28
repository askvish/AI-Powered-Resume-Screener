package com.ashok.resume_screener.controller;

import com.ashok.resume_screener.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, String>> analyzeResume(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("jobDescription") String jobDescription) {
        try {
            File tempFile = File.createTempFile("resume", file.getOriginalFilename());
            file.transferTo(tempFile);

            String result = resumeService.analyzeResume(tempFile, jobDescription);
            return ResponseEntity.ok(Map.of("status", "success", "data", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "data", "Error analyzing resume: " + e.getMessage()));
        }
    }
}
