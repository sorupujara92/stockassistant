package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.service.PdfChunkService;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.isBlank(text)) {
            return chunks;
        }

        String[] paragraphs = text.split("\\r?\\n\\r?\\n");
        StringBuilder currentChunk = new StringBuilder();

        for (String para : paragraphs) {
            para = para.trim();
            if (para.isEmpty()) continue;

            if (currentChunk.length() == 0) {
                currentChunk.append(para).append("\n\n");
            }
            else if (currentChunk.length() + para.length() < maxChunkSize) {
                currentChunk.append(para).append("\n\n");
            }
            // Rule 3: If it doesn't fit, push the current chunk to the list and start a fresh one
            else {
                chunks.add(currentChunk.toString().trim());
                currentChunk = new StringBuilder();
                currentChunk.append(para).append("\n\n");
            }
        }

        if (currentChunk.length() > 0 && StringUtils.isNotBlank(currentChunk.toString())) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }
}
