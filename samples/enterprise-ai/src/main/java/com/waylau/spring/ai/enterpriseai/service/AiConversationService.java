package com.waylau.spring.ai.enterpriseai.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * AiConversationService 多轮对话服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/

@Service
public class AiConversationService {

    // 声明但必须在构造方法里初始化
    private final StringRedisTemplate redisTemplate;
    private static final String PREFIX = "ai:conversation:";

    // 构造函数注入
    public AiConversationService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveMessage(String sessionId, String userMsg, String aiMsg) {
        redisTemplate.opsForValue().append(
                PREFIX + sessionId,
                "用户：" + userMsg + "\nAI：" + aiMsg + "\n"
        );
        redisTemplate.expire(PREFIX + sessionId, 2, TimeUnit.HOURS);
    }

    public String getHistory(String sessionId) {
        return redisTemplate.opsForValue().get(PREFIX + sessionId);
    }
}