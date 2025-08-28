//package com.ashok.resume_screener.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "resumes")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ResumeEntity {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long id;
//
//        @Column(nullable = false)
//        private String candidateName;
//
//        @Column(nullable = false)
//        private String email;
//
//        private String phone;
//
//        @Column(nullable = false)
//        private String fileName;
//
//        @Column(nullable = false)
//        private String filePath;
//
//        @Lob
//        private String extractedText;
//
//        @Enumerated(EnumType.STRING)
//        private ResumeStatus status = ResumeStatus.UPLOADED;
//
//        @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//        private List<ScreeningResult> screeningResults;
//
//        @CreationTimestamp
//        private LocalDateTime createdAt;
//
//        @UpdateTimestamp
//        private LocalDateTime updatedAt;
//}
