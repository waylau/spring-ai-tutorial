package com.waylau.spring.ai.chatbotdemo.config;

import com.waylau.spring.ai.chatbotdemo.service.ChatBotService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChatBotWebSocketHandler WebSocket Handler
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/16
 **/
@Component
public class ChatBotWebSocketHandler extends TextWebSocketHandler {

    private final ChatBotService chatBotService;
    // 存储WebSocket会话（key：sessionId，value：WebSocketSession），线程安全
    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    public ChatBotWebSocketHandler(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    /**
     * 客户端连接WebSocket时触发（生成会话ID，存储会话，返回会话ID给前端）
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 生成唯一会话ID
        String sessionId = chatBotService.createNewSession();
        // 存储WebSocket会话与会话ID的映射，便于后续关联
        sessionMap.put(sessionId, session);
        // 向客户端发送会话ID（前端需存储该ID，用于后续对话关联）
        session.sendMessage(new TextMessage("sessionId:" + sessionId));
    }

    /**
     * 接收客户端消息（用户输入），触发流式响应，返回机器人消息
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 解析客户端消息（格式：sessionId|scene|userMessage，避免消息混淆）
        String payload = message.getPayload();
        String[] parts = payload.split("\\|", 3); // 按|分割，最多分割3部分
        if (parts.length != 3) {
            // 消息格式错误，返回提示
            session.sendMessage(new TextMessage("消息格式错误，请重新发送！"));
            return;
        }
        String sessionId = parts[0]; // 会话ID
        String scene = parts[1]; // 对话场景
        String userMessage = parts[2]; // 用户消息

        // 调用聊天机器人核心服务，获取流式响应
        Flux<String> flux = chatBotService.chatStream(sessionId, userMessage, scene);
        // 逐段发送响应到客户端（实现打字机效果，逐字/逐段展示）
        flux.subscribe(
                chunk -> {
                    try {
                        // 确保会话未关闭，再发送消息
                        if (session.isOpen()) {
                            session.sendMessage(new TextMessage(chunk));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("WebSocket发送消息失败", e);
                    }
                },
                error -> {
                    // 异常处理，返回提示信息
                    try {
                        session.sendMessage(new TextMessage("对话异常，请重试！"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    /**
     * 客户端断开连接时，清理会话映射，释放资源
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 移除会话映射（根据WebSocketSession匹配）
        sessionMap.entrySet().removeIf(entry -> entry.getValue().equals(session));
    }
}