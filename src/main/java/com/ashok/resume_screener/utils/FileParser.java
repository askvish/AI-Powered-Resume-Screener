package com.ashok.resume_screener.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileParser {

    private FileParser() {
    }

    public static String extractText(File file) throws IOException {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".pdf")) {
            try (PDDocument document = PDDocument.load(file)) {
                return new PDFTextStripper().getText(document);
            }
        } else if (fileName.endsWith(".docx")) {
            try (FileInputStream fis = new FileInputStream(file);
                 XWPFDocument document = new XWPFDocument(fis)) {
                StringBuilder text = new StringBuilder();
                document.getParagraphs().forEach(p -> text.append(p.getText()).append("\n"));
                return text.toString();
            }
        } else if (fileName.endsWith(".txt")) {
            return new String(java.nio.file.Files.readAllBytes(file.toPath()));
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }
    }
}

