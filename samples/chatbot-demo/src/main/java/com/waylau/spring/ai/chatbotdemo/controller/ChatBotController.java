package com.waylau.spring.ai.chatbotdemo.controller;

import com.waylau.spring.ai.chatbotdemo.service.ChatBotService;
import com.waylau.spring.ai.chatbotdemo.service.SessionCleanService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * ChatBotController ChatBot Controller
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/16
 **/
@RestController
@RequestMapping("/ai/chatbot") // 接口统一前缀
public class ChatBotController {

    private final ChatBotService chatBotService;
    private final SessionCleanService cleanService;

    public ChatBotController(ChatBotService chatBotService, SessionCleanService cleanService) {
        this.chatBotService = chatBotService;
        this.cleanService = cleanService;
    }

    /**
     * 1. 生成新会话（供前端初始化会话使用）
     *
     * @return 会话ID
     */
    @GetMapping("/session/new")
    public Map<String, String> createNewSession() {
        return Map.of("sessionId", chatBotService.createNewSession());
    }

    /**
     * 2. 多轮对话（非流式，HTTP POST，适合一次性问答）
     *
     * @param request 聊天请求参数（sessionId、userMessage、scene）
     * @return 机器人完整响应
     */
    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody ChatRequest request) {
        // 调用聊天机器人服务，收集完整响应（非流式）
        Flux<String> flux = chatBotService.chatStream(
                request.getSessionId(),
                request.getUserMessage(),
                request.getScene()
        );
        // 拼接流式结果，返回完整响应
        String botResponse = flux.collectList().block().stream().reduce("", String::concat);
        return Map.of("response", botResponse);
    }

    /**
     * 3. 多轮对话（流式，HTTP SSE，适合非WebSocket场景的流式输出）
     *
     * @param sessionId   会话ID
     * @param userMessage 用户消息
     * @param scene       对话场景（默认default）
     * @return 流式响应结果
     */
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(
            @RequestParam String sessionId,
            @RequestParam String userMessage,
            @RequestParam(defaultValue = "default") String scene) {
        return chatBotService.chatStream(sessionId, userMessage, scene);
    }

    /**
     * 4. 获取会话历史记录（供前端展示历史对话）
     *
     * @param sessionId 会话ID
     * @return 会话历史列表
     */
    @GetMapping("/session/history")
    public List<String> getSessionHistory(@RequestParam String sessionId) {
        return chatBotService.getSessionHistory(sessionId);
    }

    /**
     * 5. 清理会话（用户主动清理历史对话）
     *
     * @param sessionId 会话ID
     * @return 清理结果提示
     */
    @PostMapping("/session/clean")
    public Map<String, String> cleanSession(@RequestParam String sessionId) {
        cleanService.cleanSession(sessionId);
        return Map.of("message", "会话清理成功");
    }

    /**
     * 聊天请求参数封装（用于HTTP POST接口接收参数）
     */
    public static class ChatRequest {
        private String sessionId; // 会话ID
        private String userMessage; // 用户消息
        private String scene = "default"; // 对话场景，默认default

        // getter/setter方法（必须提供，否则无法接收参数）
        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getUserMessage() {
            return userMessage;
        }

        public void setUserMessage(String userMessage) {
            this.userMessage = userMessage;
        }

        public String getScene() {
            return scene;
        }

        public void setScene(String scene) {
            this.scene = scene;
        }
    }
}