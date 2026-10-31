package com.waylau.spring.ai.enterpriseai;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration;
import org.springframework.ai.model.zhipuai.autoconfigure.ZhiPuAiChatAutoConfiguration;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.support.RetryTemplate;

@SpringBootApplication
public class EnterpriseAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseAiApplication.class, args);
    }

    /**
     * 异常与重试机制
     * @return
     */
    @Bean
    public RetryTemplate aiRetryTemplate() {
        return RetryTemplate.builder()
                .maxAttempts(3)
                .fixedBackoff(1000)
                .retryOn(TransientAiException.class)
                .build();
    }

    /**
     * 接口限流保护
     * @return
     */
    @Bean
    public RateLimiter aiRateLimiter() {
        return RateLimiter.create(10.0); // 每秒10个请求
    }
}
