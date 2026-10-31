package com.waylau.spring.ai.chatbotdemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * ChatBotService 聊天机器人核心服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/16
 **/
@Service
public class ChatBotService {

    private final ChatClient chatClient;
    private final ConversationMemoryService memoryService;
    private final SessionCleanService cleanService;

    // 构造注入ChatClient、会话记忆服务、会话清理服务
    public ChatBotService(ChatClient.Builder chatClientBuilder,
                          ConversationMemoryService memoryService,
                          SessionCleanService cleanService) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptions.builder()
                        .temperature(0.6)
                        .build()) // 与配置文件一致，避免上下文超限
                .build();
        this.memoryService = memoryService;
        this.cleanService = cleanService;
    }

    /**
     * 多轮对话流式响应（核心方法，支持场景适配、上下文关联）
     *
     * @param sessionId   会话ID（关联历史对话）
     * @param userMessage 当前用户消息
     * @param scene       对话场景（客服/闲聊/咨询）
     * @return 流式响应结果（用于前端打字机效果）
     */
    public Flux<String> chatStream(String sessionId, String userMessage, String scene) {
        // 1. 清理过长会话，避免上下文超出模型限制
        cleanService.cleanLongSession(sessionId);
        // 2. 提取历史会话上下文（关联之前的对话）
        String context = memoryService.getContext(sessionId);
        // 3. 构造场景化Prompt（不同场景，不同响应风格）
        String scenePrompt = getScenePrompt(scene);
        // 4. 拼接完整Prompt（场景提示+上下文+当前用户消息，引导模型正确响应）
        String prompt = scenePrompt + "\n" + context + userMessage + "\n机器人：";
        // 5. 流式调用Ollama模型，获取响应结果
        Flux<String> contentFlux = chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
        // 6. 响应完成后，将当前对话（用户消息+机器人响应）存储到会话记忆
        contentFlux.doOnComplete(() -> {
            // 收集流式结果，拼接完整的机器人响应
            String botResponse = contentFlux.collectList().block().stream().reduce("", String::concat);
            // 存储对话到Redis，并刷新会话过期时间
            memoryService.saveMessage(sessionId, userMessage, botResponse);
        });
        return contentFlux;
    }

    /**
     * 场景化Prompt构造（根据场景返回不同的提示词，控制响应风格）
     *
     * @param scene 场景（客服/闲聊/咨询/默认）
     * @return 场景提示词（引导模型输出符合场景的响应）
     */
    private String getScenePrompt(String scene) {
        return switch (scene) {
            case "customerService" ->
                    "你是企业客服机器人，语气正式、专业，耐心解答用户的订单查询、产品咨询、售后问题，无法解答时回复‘抱歉，我暂时无法解答你的问题，请联系人工客服。’，不添加多余内容。";
            case "chat" ->
                    "你是闲聊机器人，语气活泼、亲切，像朋友一样和用户聊天，避免使用专业术语，主动引导话题，回复简洁自然，不冗长。";
            case "consult" ->
                    "你是咨询机器人，语气专业、简洁，精准解答用户的咨询问题，提供具体、可操作的建议，不添加无关内容。";
            default ->
                    "你是智能聊天机器人，根据用户需求友好响应，回复简洁明了，避免冗长，无法理解时回复‘抱歉，我没明白你的意思，请再说明一下。’。";
        };
    }

    /**
     * 生成新会话ID（供前端初始化会话使用）
     */
    public String createNewSession() {
        return memoryService.generateSessionId();
    }

    /**
     * 获取会话历史记录（供前端展示历史对话）
     *
     * @param sessionId 会话ID
     * @return 会话历史列表
     */
    public List<String> getSessionHistory(String sessionId) {
        return memoryService.getSessionHistory(sessionId);
    }
}