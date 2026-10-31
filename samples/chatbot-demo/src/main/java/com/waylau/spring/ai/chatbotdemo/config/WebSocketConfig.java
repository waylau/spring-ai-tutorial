package com.waylau.spring.ai.chatbotdemo.config;

/**
 * WebSocketConfig WebSocket Config
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/16
 **/

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // 开启WebSocket支持
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatBotWebSocketHandler chatBotWebSocketHandler;

    // 注入WebSocket处理器
    public WebSocketConfig(ChatBotWebSocketHandler chatBotWebSocketHandler) {
        this.chatBotWebSocketHandler = chatBotWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册WebSocket接口，路径为/ws/chat，允许跨域（前端可正常访问）
        registry.addHandler(chatBotWebSocketHandler, "/ws/chat")
                .setAllowedOrigins("*");
    }
}