package com.waylau.spring.ai.prompttemplate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waylau.spring.ai.prompttemplate.dto.MarketingRequest;
import com.waylau.spring.ai.prompttemplate.dto.TechRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ContentGeneratorControllerTest 真实调用LLM的集成测试
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
@SpringBootTest
@AutoConfigureMockMvc
public class ContentGeneratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("真实调用：测试营销文案生成接口")
    @Tag("integration") // 【最佳实践】：打上标签，方便在日常开发时跳过此测试
    public void testGenerateMarketing_RealCall() throws Exception {
        // 1. 准备请求参数
        MarketingRequest request = new MarketingRequest(
                "幽默风趣",
                "Z世代大学生",
                200,
                "AI学习助手",
                List.of("24小时秒回", "支持拍照解题")
        );

        // 2. 执行请求并验证
        mockMvc.perform(post("/api/generate/marketing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(result -> System.out.println("LLM真实输出: " + result.getResponse().getContentAsString())) // 打印看结果
                .andExpect(status().isOk()) // 验证接口没抛异常
                .andExpect(content().string(org.hamcrest.Matchers.notNullValue())) // 验证返回了内容
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.emptyString()))); // 验证不是空字符串
    }

    @Test
    @DisplayName("真实调用：测试技术摘要生成接口")
    @Tag("integration")
    public void testGenerateTechSummary_RealCall() throws Exception {
        TechRequest request = new TechRequest(
                "微服务架构",
                "我们计划使用 Spring Cloud Alibaba 重构单体应用。"
        );

        mockMvc.perform(post("/api/generate/tech-summary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(result -> System.out.println("LLM真实输出: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }
}
