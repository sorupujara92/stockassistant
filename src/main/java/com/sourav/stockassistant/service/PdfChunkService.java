package com.sourav.stockassistant.service;

import java.io.IOException;
import java.util.List;

public interface PdfChunkService {
    List<String> extractSemanticChunks(String pdfPath) throws IOException;
}
