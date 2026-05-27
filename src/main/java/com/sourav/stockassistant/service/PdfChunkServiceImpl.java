package com.sourav.stockassistant.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfChunkServiceImpl implements PdfChunkService {

    @Override
    public List<String> extractSemanticChunks(String pdfPath) throws IOException {
        String text = readPdf(pdfPath);
        return semanticChunk(text, 1500); // approx chars per chunk
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

        String[] paragraphs = text.split("\\r?\\n\\r?\\n");

        StringBuilder currentChunk = new StringBuilder();

        for (String para : paragraphs) {
            para = para.trim();

            if (para.isEmpty()) continue;

            if (currentChunk.length() + para.length() < maxChunkSize) {
                currentChunk.append(para).append("\n\n");
            } else {
                chunks.add(currentChunk.toString().trim());

                currentChunk = new StringBuilder();
                currentChunk.append(para).append("\n\n");
            }
        }

        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }
}