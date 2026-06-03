package com.sourav.stockassistant.service.impl;

import com.sourav.stockassistant.service.LLMService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LLMServiceImpl implements LLMService {

    @Autowired
    private OpenAiChatModel model;

    @Override
    public String ask(String prompt) {

        return model.chat(prompt);
    }
}