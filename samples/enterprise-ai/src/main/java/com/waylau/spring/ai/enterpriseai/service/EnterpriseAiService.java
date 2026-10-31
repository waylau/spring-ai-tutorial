package com.waylau.spring.ai.enterpriseai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * EnterpriseAiService 企业AI服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/18
 **/
@Service
public class EnterpriseAiService {

    private final ChatClient chatClient;

    public EnterpriseAiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // 通用对话
    public String chat(String message) {
        return chatClient.prompt().user(message).call().content();
    }

    // 文本总结
    public String summarize(String text) {
        return chatClient.prompt()
                .user("提取文本关键词，逗号分隔：" + text)
                .call().content();
    }

    // 关键词提取
    public String extractKeywords(String text) {
        return chatClient.prompt()
                .user("提取文本关键词，逗号分隔：" + text)
                .call().content();
    }
}