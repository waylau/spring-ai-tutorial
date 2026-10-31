package com.waylau.spring.ai.enterpriseai.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * AiChatHistory AiChatHistory实体
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
@Data
@Entity
@Table(name = "ai_chat_history")
public class AiChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String sessionId;

    @Column(columnDefinition = "TEXT")
    private String userMessage;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    private LocalDateTime createTime;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now();
    }
}
