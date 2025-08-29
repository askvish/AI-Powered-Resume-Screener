package com.ashok.resume_screener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AIService {

    private final WebClient webClient;

    public AIService(@Value("${api.token}") String token) {
        this.webClient = WebClient.builder()
                .baseUrl("https://router.huggingface.co/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + token)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String screenResume(String resumeText, String jobDescription) {
        String prompt = "Compare this resume against the job description. "
                + "Give a match score out of 100 and list missing skills.\n\n"
                + "Resume:\n" + resumeText + "\n\n"
                + "Job Description:\n" + jobDescription;

        Map<String, Object> request = Map.of(
                "model", "deepseek-ai/DeepSeek-V3-0324",  // or another HuggingFace model
                "messages", new Object[]{
                        Map.of("role", "user", "content", prompt)
                }
        );

        return webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("Error: " + e.getMessage()))
                .block();
    }
}
