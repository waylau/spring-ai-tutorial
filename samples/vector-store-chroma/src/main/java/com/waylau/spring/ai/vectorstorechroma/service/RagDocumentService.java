package com.waylau.spring.ai.vectorstorechroma.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * RagDocumentService RAG文档服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/10
 **/
@Service
public class RagDocumentService {
    private final VectorStore vectorStore;

    // 注入 Spring Boot 自动装配的 Chroma VectorStore
    public RagDocumentService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    /**
     * 模拟企业知识库文档入库
     */
    public void loadKnowledge() {
        List<Document> documents = List.of(
                new Document("Spring AI是Spring生态中用于构建AI应用的框架，它极大地简化了与各种大语言模型的集成。",
                        Map.of("source", "spring-ai-docs", "type", "framework")),
                new Document("MySQL是一种广泛使用的开源关系型数据库，使用SQL语言进行数据操作。",
                        Map.of("source", "db-docs", "type", "database")),
                new Document("Redis是一个高性能的键值对内存数据库，常用于缓存、消息队列和分布式锁。",
                        Map.of("source", "db-docs", "type", "database")),
                new Document("大语言模型的训练需要海量的文本数据和巨大的算力支持。",
                        Map.of("source", "llm-theory", "type", "theory"))
        );
        // 核心：只需一行代码，Spring AI 会自动调用 EmbeddingModel 生成向量，并存入 Chroma
        vectorStore.add(documents);
        System.out.println("知识库加载完成！");
    }

    /**
     * 语义相似性检索
     */
    public void searchKnowledge(String userQuery) {
        System.out.println("用户提问: " + userQuery);
        // 核心：只需一行代码，Spring AI 会将 userQuery 转为向量，去 Chroma 中检索最相似的文档
        // 默认返回 Top 4 结果
        List<Document> results = vectorStore.similaritySearch(userQuery);
        System.out.println("检索到的相关文档:");
        for (Document doc : results) {
            System.out.println(" - 内容: " + doc.getText());
            System.out.println("   元数据: " + doc.getMetadata());
        }
    }
}