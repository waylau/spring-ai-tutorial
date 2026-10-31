package com.waylau.spring.ai.assistanttools.service;

import com.waylau.spring.ai.assistanttools.AssistantTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.stereotype.Service;

/**
 * AgentService 智能体服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/14
 **/
@Service
public class AgentService {
    private final ChatClient agentClient;

    public AgentService(ChatClient.Builder builder, AssistantTools assistantTools) {

        // 底层的 defaultTools() 会自动帮你转换和解析 @Tool 注解
        this.agentClient = builder
                .defaultSystem("你是一个强大的智能助手。你可以使用内置的工具来查询真实数据。请优先使用工具获取准确答案，不要自己瞎编。")
                .defaultTools(assistantTools)
                .build();

    }

    public String chat(String userMessage) {
        return agentClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }
}