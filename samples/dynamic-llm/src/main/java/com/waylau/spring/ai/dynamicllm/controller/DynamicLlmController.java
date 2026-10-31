package com.waylau.spring.ai.dynamicllm.controller;

import com.waylau.spring.ai.dynamicllm.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * DynamicLlmController 多LLM控制器
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
@RestController
@RequestMapping("/api/chat")
public class DynamicLlmController {
    private final Map<String, ChatClient> chatClientMap;

    // Spring 会自动将上面配置类中的 Map 注入到这里
    public DynamicLlmController(Map<String, ChatClient> chatClientMap) {
        this.chatClientMap = chatClientMap;
    }

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {
        String provider = request.getProvider();

        // 1. 路由校验
        if (!chatClientMap.containsKey(provider)) {
            throw new IllegalArgumentException("不支持的模型提供商: " + provider +
                    "，当前可用: " + chatClientMap.keySet());
        }
        // 2. 获取对应的客户端
        ChatClient client = chatClientMap.get(provider);
        // 3. 统一调用逻辑
        return client.prompt()
                .user(request.getMessage())
                .call()
                .content();
    }
}
