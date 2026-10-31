package com.waylau.spring.ai.enterpriseai.repository;

import com.waylau.spring.ai.enterpriseai.entity.AiChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AiChatHistoryRepository AiChatHistory Repository
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
public interface AiChatHistoryRepository extends JpaRepository<AiChatHistory, Long> {
    List<AiChatHistory> findByUserIdOrderByCreateTimeDesc(String userId);
}