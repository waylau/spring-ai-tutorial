package com.waylau.spring.ai.knowledgeingest.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KnowledgeIngestService 知识入库服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/14
 **/
@Service
public class KnowledgeIngestService {
    private final VectorStore vectorStore;

    public KnowledgeIngestService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingestTextFile(String filePath) {
        // 1. 读取文档
        TextReader reader = new TextReader(new ClassPathResource(filePath));
        //TextReader reader = new TextReader(new FileSystemResource(filePath));
        List<Document> documents = reader.get();
        // 2. 文本切分（使用最古老、最稳定的 TextSplitter）
        // 参数说明：1000表示每个块目标400个字符，10表示块与块之间重叠5000个字符，true表示保留换行符等分隔符
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(1000)
                .withMinChunkSizeChars(400)
                .withMinChunkLengthToEmbed(10)
                .withMaxNumChunks(5000)
                .withKeepSeparator(true)
                .build();

        List<Document> chunks = splitter.apply(documents);
        // 3. 向量化并存入向量库（SimpleVectorStore会自动调用配置好的Embedding模型）
        vectorStore.add(chunks);

        System.out.println("成功入库文档片段数量：" + chunks.size());
    }
}