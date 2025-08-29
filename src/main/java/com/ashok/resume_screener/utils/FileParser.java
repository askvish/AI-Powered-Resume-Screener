package com.ashok.resume_screener.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Normalizer;

public class FileParser {

    private FileParser() {
    }

    public static String extractText(File file) throws IOException {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".pdf")) {
            try (PDDocument document = PDDocument.load(file)) {
                String rawText = new PDFTextStripper().getText(document);

                // 1. Normalize Unicode (fix ligatures, accents, etc.)
                String normalized = Normalizer.normalize(rawText, Normalizer.Form.NFKC);

                // 2. Remove control/non-printable characters EXCEPT newline (\n)
                String cleaned = normalized.replaceAll("[\\p{C}&&[^\\n]]", " ");

                // 3. Clean up spaces (but keep line breaks)
                cleaned = cleaned.replaceAll("[ \\t\\x0B\\f\\r]+", " ");

                // 4. Trim leading/trailing spaces on each line (optional)
                cleaned = cleaned.replaceAll("(?m)^\\s+|\\s+$", "");

                return cleaned.trim();
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

