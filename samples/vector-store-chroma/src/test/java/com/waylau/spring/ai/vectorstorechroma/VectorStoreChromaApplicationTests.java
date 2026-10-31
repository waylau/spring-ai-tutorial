package com.waylau.spring.ai.vectorstorechroma;

import com.waylau.spring.ai.vectorstorechroma.service.RagDocumentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VectorStoreChromaApplicationTests {

    @Autowired
    private RagDocumentService ragDocumentService;
    
    @Test
    void testKnowledge() {
        // 初始化入库
        ragDocumentService.loadKnowledge();

        // 测试语义检索
        ragDocumentService.searchKnowledge("如何使用Java对接AI大模型？");
    }

}
