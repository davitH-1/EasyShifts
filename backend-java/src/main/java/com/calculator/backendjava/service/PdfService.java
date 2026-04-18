package com.calculator.backendjava.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import java.io.InputStream;

@Service
public class PdfService {
    public String extractText(InputStream pdfStream) throws Exception {
        // PDFBox 3.x uses Loader.loadPDF instead of PDDocument.load
        try (PDDocument document = Loader.loadPDF(pdfStream.readAllBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}