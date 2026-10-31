package com.waylau.spring.ai.chatbotdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时任务（用于会话定时清理）
public class ChatbotDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotDemoApplication.class, args);
    }

}
