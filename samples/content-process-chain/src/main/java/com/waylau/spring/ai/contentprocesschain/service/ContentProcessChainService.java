package com.waylau.spring.ai.contentprocesschain.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * ContentProcessChainService 客服工单标题生成服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/13
 **/
@Service
public class ContentProcessChainService {

    // 只注入一个基础的 ChatClient 实例
    private final ChatClient chatClient;

    public ContentProcessChainService(ChatClient.Builder builder) {
        // 直接 build 一个通用客户端，不要在这里写 system
        this.chatClient = builder.build();
    }

    public String processFeedback(String rawFeedback) {
        System.out.println("【原始输入】：" + rawFeedback);

        // 链条环节1：提取关键信息
        // 正确写法：在 prompt() 之后调用 system()
        String facts = chatClient.prompt()
                .system("你是一个信息提取专家，只输出提取出的事实要点，不要有任何多余的解释。")
                .user(rawFeedback)
                .call()
                .content();
        System.out.println("【环节1-提取事实】：" + facts);

        // 链条环节2：情感分析
        String sentiment = chatClient.prompt()
                .system("你是一个情感分析专家，请判断以下文本的情感倾向，只用一个词输出：正面、负面 或 中性。")
                .user(facts)
                .call()
                .content();
        System.out.println("【环节2-情感分析】：" + sentiment);

        // 链条环节3：生成工单标题
        String finalTitle = chatClient.prompt()
                .system("你是一个客服工单系统。请根据提供的事实要点和情感倾向，生成一个不超过15个字的工单标题。")
                .user("事实：" + facts + "；情感：" + sentiment)
                .call()
                .content();
        System.out.println("【环节3-最终标题】：" + finalTitle);

        return finalTitle;
    }
}
