package com.waylau.spring.ai.enterpriseai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * AiChatModelConfig 对话模型配置类
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
@Configuration
public class AiChatModelConfig {

    // 开发环境：只装配使用 Ollama 的 ChatClient
    @Bean
    @Profile("dev")
    public ChatClient devChatClient(
            // 精准指定注入名叫 ollamaChatModel 的Bean
            @Qualifier("ollamaChatModel") ChatModel chatModel) {
        System.out.println(">>> 路由成功：当前使用本地 Ollama 模型");
        return ChatClient.create(chatModel);
    }

    // 生产环境：只装配使用 ZhipuAI 的 ChatClient
    @Bean
    @Profile("prod")
    public ChatClient prodChatClient(
            // 精准指定注入名叫 zhiPuAiChatModel 的Bean
            @Qualifier("zhiPuAiChatModel") ChatModel chatModel) {
        System.out.println(">>> 路由成功：当前使用 ZhipuAI GLM-4 模型");
        return ChatClient.create(chatModel);
    }
}
