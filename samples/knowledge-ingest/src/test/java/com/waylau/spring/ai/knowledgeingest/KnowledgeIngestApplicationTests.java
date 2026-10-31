package com.waylau.spring.ai.knowledgeingest;

import com.waylau.spring.ai.knowledgeingest.service.KnowledgeIngestService;
import com.waylau.spring.ai.knowledgeingest.service.RagQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KnowledgeIngestApplicationTests {

    @Autowired
    private KnowledgeIngestService ingestService;

    @Autowired
    private RagQueryService queryService;

    @Test
    void testFullRagPipeline() {
        // 1. 先执行知识入库
        ingestService.ingestTextFile("data/employee_rules.txt");

        // 2. 测试能够正确回答知识库内的问题
        String answer1 = queryService.ask("我入职3年了，能休几天年假？");
        System.out.println("问题1回答: " + answer1);
        // 预期输出：包含“5天”相关的描述

        String answer2 = queryService.ask("出差住了一晚600块的酒店，怎么报销？");
        System.out.println("问题2回答: " + answer2);
        // 预期输出：包含“超标”、“部门总监签字”等描述

        // 3. 测试对知识库外问题的拒答（验证是否消除幻觉）
        String answer3 = queryService.ask("公司今年的年终奖发多少？");
        System.out.println("问题3回答: " + answer3);
        // 预期输出：明确表示知识库中没有相关信息
    }
}
