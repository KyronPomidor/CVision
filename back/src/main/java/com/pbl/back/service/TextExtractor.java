package com.pbl.back.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class TextExtractor {

    public String extract(MultipartFile file) {
        String mimeType = file.getContentType();
        try {
            if ("application/pdf".equals(mimeType)) {
                try (PDDocument document = Loader.loadPDF(file.getBytes())) {
                    return new PDFTextStripper().getText(document);
                }
            }
            if ("application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(mimeType)) {
                try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(file.getBytes()));
                     XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
                    return extractor.getText();
                }
            }
            if ("text/plain".equals(mimeType)) {
                return new String(file.getBytes(), StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Could not extract text from CV", ex);
        }
        throw new IllegalArgumentException("Unsupported file type");
    }
}
