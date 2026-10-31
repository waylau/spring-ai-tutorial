package com.waylau.spring.ai.copygenerator;

import com.waylau.spring.ai.copygenerator.service.CopyGeneratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CopyGeneratorApplicationTests {

    @Autowired
    private CopyGeneratorService copyGeneratorService;

    @Test
    void generateStream_usingOllama_shouldReturnContent() {
        Flux<String> flux = copyGeneratorService.generateStream(
                "产品宣传语",
                "智能AI知识库",
                "专业简洁"
        );

        // 收集流式结果
        String result = flux.collectList().block().toString();

        System.out.println("生成结果：" + result);
        assertNotNull(result);
        assertTrue(result.length() > 10);
    }

}
