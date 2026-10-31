package com.waylau.spring.ai.dynamicllm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * MultiLlmConfig 多LLM配置类
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
@Configuration
public class MultiLlmConfig {
    // 注入 Spring 自动装配的两个底层模型实例
    private final ZhiPuAiChatModel zhiPuModel;
    private final OllamaChatModel ollamaModel;

    public MultiLlmConfig(ZhiPuAiChatModel zhiPuModel, OllamaChatModel ollamaModel) {
        this.zhiPuModel = zhiPuModel;
        this.ollamaModel = ollamaModel;
    }

    @Bean
    public Map<String, ChatClient> chatClientMap() {
        return Map.of(
                "zhipuai", ChatClient.create(zhiPuModel),
                "ollama-qwen", ChatClient.create(ollamaModel)
        );
    }
}