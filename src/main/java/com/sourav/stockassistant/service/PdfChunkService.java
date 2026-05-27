package com.sourav.stockassistant.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface PdfChunkService {
    List<String> extractSemanticChunks(String pdfPath) throws IOException;
}
