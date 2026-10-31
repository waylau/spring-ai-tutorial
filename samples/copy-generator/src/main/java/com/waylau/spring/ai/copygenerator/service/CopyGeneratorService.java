package com.waylau.spring.ai.copygenerator.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * CopyGeneratorService 智能文案生成服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/15
 **/
@Service
public class CopyGeneratorService {
    private final ChatClient chatClient;

    public CopyGeneratorService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // 流式文案生成
    public Flux<String> generateStream(String type, String keyword, String style) {
        String prompt = "你是专业文案师，生成" + type + "。\n" +
                "关键词：" + keyword + "\n" +
                "风格：" + style + "\n" +
                "要求：简洁有吸引力，直接输出结果。";

        return chatClient.prompt()
                .user(prompt)
                .options(ChatOptions.builder().temperature(0.7).build())
                .stream()
                .content();


    }
}