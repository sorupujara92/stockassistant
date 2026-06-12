package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.service.PdfChunkService;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfChunkServiceImpl implements PdfChunkService {

    @Override
    public List<String> extractSemanticChunks(String pdfPath) throws IOException {
        String text = readPdf(pdfPath);
        return semanticChunk(text, 1500);
    }

    private String readPdf(String pdfPath) throws IOException {
        File file = new File(pdfPath);
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private List<String> semanticChunk(String text, int maxChunkSize) {
        List<String> chunks = new ArrayList<>();

        for (int start = 0; start < text.length(); start += maxChunkSize) {

            int end = Math.min(
                    start + maxChunkSize,
                    text.length()
            );

            chunks.add(text.substring(start, end));
        }

        return chunks;
    }
}
