package com.waylau.spring.ai.chatbotdemo.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * SessionCleanService 会话清理服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/16
 **/
@Service
public class SessionCleanService {

    private final StringRedisTemplate redisTemplate;
    // 会话存储的Redis键前缀（与ConversationMemoryService保持一致）
    private static final String SESSION_KEY_PREFIX = "chatbot:session:";

    public SessionCleanService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 1. 用户主动清理会话（前端触发，清除指定会话的所有历史记录）
     *
     * @param sessionId 会话ID
     */
    public void cleanSession(String sessionId) {
        String sessionKey = SESSION_KEY_PREFIX + sessionId;
        redisTemplate.delete(sessionKey); // 删除Redis中的会话记录
    }

    /**
     * 2. 定时清理过期会话（每天凌晨2点执行，清理已过期的会话，释放Redis资源）
     * 采用Cron表达式：0 0 2 * * ? （每天凌晨2点整执行）
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void scheduledCleanExpiredSession() {
        // 匹配所有会话键（模糊查询，获取所有会话的Redis键）
        Set<String> sessionKeys = redisTemplate.keys(SESSION_KEY_PREFIX + "*");
        if (sessionKeys == null || sessionKeys.isEmpty()) {
            return; // 无会话记录，直接返回
        }
        // 遍历所有会话，删除已过期的会话（Redis中expire<0表示已过期）
        for (String key : sessionKeys) {
            Boolean isExpired = redisTemplate.getExpire(key) < 0;
            if (Boolean.TRUE.equals(isExpired)) {
                redisTemplate.delete(key);
            }
        }
    }

    /**
     * 3. 清理过长会话（保留最新10轮对话，避免上下文超出模型num_ctx限制）
     *
     * @param sessionId 会话ID
     */
    public void cleanLongSession(String sessionId) {
        String sessionKey = SESSION_KEY_PREFIX + sessionId;
        // 获取会话总长度（每轮对话2条记录：用户消息+机器人响应）
        Long size = redisTemplate.opsForList().size(sessionKey);
        if (size == null || size <= 20) { // 保留10轮对话（20条记录），可灵活调整
            return;
        }
        // 删除最早的记录，保留最新20条（trim方法：保留start到end的记录）
        redisTemplate.opsForList().trim(sessionKey, size - 20, size - 1);
    }
}