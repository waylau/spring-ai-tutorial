package com.waylau.spring.ai.codeassistant;

import com.waylau.spring.ai.codeassistant.service.CodeAssistantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CodeAssistantApplicationTests {

    @Autowired
    private CodeAssistantService codeAssistantService;

    @Test
    void generateCode() {
        String result = codeAssistantService.generateCode(
                "Java",
                "写一个Hello World的例子"
        );

        System.out.println("生成结果：" + result);
        assertNotNull(result);
        assertTrue(result.length() > 10);
    }

}
