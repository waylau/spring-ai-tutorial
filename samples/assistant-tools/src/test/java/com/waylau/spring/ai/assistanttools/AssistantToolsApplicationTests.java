package com.waylau.spring.ai.assistanttools;

import com.waylau.spring.ai.assistanttools.service.AgentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AssistantToolsApplicationTests {

    @Autowired
    private AgentService agentService;

    @Test
    void testAgentTools() {
        // 测试算术能力
        String answer1 = agentService.chat("帮我算一下 125 乘以 88 是多少？");
        System.out.println("计算结果: " + answer1);
        // 预期：模型会调用calculate工具，并回复 11000
        // 测试天气能力
        String answer2 = agentService.chat("我在北京，今天出门需要带伞吗？");
        System.out.println("天气回复: " + answer2);
        // 预期：模型会调用getWeather工具，得知北京晴天，回复不需要带伞
        // 测试无工具时的常规对话
        String answer3 = agentService.chat("你好，你能帮我干什么？");
        System.out.println("常规回复: " + answer3);
        // 预期：模型不调用工具，直接总结自己的能力
    }

}
