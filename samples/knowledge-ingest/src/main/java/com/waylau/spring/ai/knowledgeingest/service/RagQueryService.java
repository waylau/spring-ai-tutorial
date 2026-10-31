package com.waylau.spring.ai.knowledgeingest.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

/**
 * RagQueryService 智能问答服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/14
 **/
@Service
public class RagQueryService {

    private final ChatClient ragClient;

    public RagQueryService(ChatClient.Builder builder, VectorStore vectorStore) {
        // 构建带有 RAG 拦截器的 ChatClient
        this.ragClient = builder
                .defaultSystem("你是一个严谨的企业内部助手。请只根据我提供的参考资料来回答问题，不要编造任何信息。如果资料中找不到答案，请直接说'我无法在知识库中找到相关信息'。")
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build()) // 核心：绑定向量库
                .build();
    }

    public String ask(String userQuestion) {
        return ragClient.prompt()
                .user(userQuestion)
                .call()
                .content();
    }
}