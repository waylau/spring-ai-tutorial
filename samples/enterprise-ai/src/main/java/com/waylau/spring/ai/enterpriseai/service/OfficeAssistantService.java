package com.waylau.spring.ai.enterpriseai.service;

import com.waylau.spring.ai.enterpriseai.entity.AiChatHistory;
import com.waylau.spring.ai.enterpriseai.repository.AiChatHistoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * OfficeAssistantService 智能办公服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
@Service
public class OfficeAssistantService {

    private final ChatClient chatClient;
    private final AiConversationService conversationService;
    private final AiChatHistoryRepository historyRepository;

    // 构造函数初始化所有 final 字段
    public OfficeAssistantService(
            ChatClient chatClient,
            AiConversationService conversationService,
            AiChatHistoryRepository historyRepository
    ) {
        this.chatClient = chatClient;
        this.conversationService = conversationService;
        this.historyRepository = historyRepository;
    }

    // 办公助手对话（带记忆）
    public String chat(String userId, String sessionId, String message) {
        String history = conversationService.getHistory(sessionId);
        String prompt = "历史对话：\n" + history + "\n当前请求：" + message;

        String reply = chatClient.prompt().user(prompt).call().content();

        conversationService.saveMessage(sessionId, message, reply);
        saveHistory(userId, sessionId, message, reply);
        return reply;
    }

    // 生成工作邮件
    public String generateEmail(String content) {
        return chatClient.prompt()
                .user("生成一封正式工作邮件：" + content)
                .call().content();
    }

    // 保存对话记录
    private void saveHistory(String userId, String sessionId, String userMsg, String aiMsg) {
        AiChatHistory history = new AiChatHistory();
        history.setUserId(userId);
        history.setSessionId(sessionId);
        history.setUserMessage(userMsg);
        history.setAiResponse(aiMsg);
        history.setCreateTime(LocalDateTime.now());
        historyRepository.save(history);
    }
}