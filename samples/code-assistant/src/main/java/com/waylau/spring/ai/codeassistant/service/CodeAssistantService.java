package com.waylau.spring.ai.codeassistant.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

/**
 * CodeAssistantService 代码生成服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/15
 **/
@Service
public class CodeAssistantService {
    private final ChatClient chatClient;

    public CodeAssistantService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultOptions(ChatOptions.builder()
                        .temperature(0.1) // 代码必须精准
                        .maxTokens(2048)
                        .build())
                .build();
    }

    // 代码生成
    public String generateCode(String language, String requirement) {
        String prompt = "你是编程高手，生成" + language + "代码，只返回代码。\n" +
                "需求：" + requirement + "\n" +
                "代码规范清晰，带注释。";

        return chatClient.prompt().user(prompt).call().content();
    }

    // 代码优化
    public String optimizeCode(String code) {
        return chatClient.prompt()
                .user("优化以下代码，提升性能、可读性、规范：\n" + code)
                .call()
                .content();
    }
}