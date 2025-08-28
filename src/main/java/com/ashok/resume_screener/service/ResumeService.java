package com.ashok.resume_screener.service;

import com.ashok.resume_screener.utils.FileParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ResumeService {

    private final AIService aiService;
    private final ObjectMapper objectMapper;

    public ResumeService(AIService aiService, ObjectMapper objectMapper) {
        this.aiService = aiService;
        this.objectMapper = objectMapper;
    }

    public String analyzeResume(File resumeFile, String jobDescription) throws Exception {
        String resumeText = FileParser.extractText(resumeFile);
        String response = aiService.screenResume(resumeText, jobDescription);

        JsonNode jsonNode = objectMapper.readTree(response);

        return jsonNode.get("choices").get(0).get("message").get("content").asText();
    }
}

