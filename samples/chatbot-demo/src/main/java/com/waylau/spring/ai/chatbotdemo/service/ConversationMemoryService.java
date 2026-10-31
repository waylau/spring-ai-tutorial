package com.waylau.spring.ai.chatbotdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ConversationMemoryService 会话记忆服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/16
 **/
@Service
public class ConversationMemoryService {

    private final StringRedisTemplate redisTemplate;
    // 会话存储的Redis键前缀（用于区分不同会话）
    private static final String SESSION_KEY_PREFIX = "chatbot:session:";
    // 会话过期时间（从配置文件读取，可灵活调整）
    private final Integer sessionExpireTime;

    // 构造注入Redis模板与会话过期时间
    public ConversationMemoryService(StringRedisTemplate redisTemplate,
                                     @Value("${spring.ai.chatbot.session.expire-time}") Integer sessionExpireTime) {
        this.redisTemplate = redisTemplate;
        this.sessionExpireTime = sessionExpireTime;
    }

    /**
     * 生成唯一会话ID（用于区分不同用户的会话，采用UUID生成，避免重复）
     */
    public String generateSessionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 存储单轮对话到会话记忆（用户消息+机器人响应）
     *
     * @param sessionId   会话ID（关联具体会话）
     * @param userMessage 用户发送的消息
     * @param botResponse 机器人的响应消息
     */
    public void saveMessage(String sessionId, String userMessage, String botResponse) {
        // 拼接Redis键（前缀+会话ID，确保唯一）
        String sessionKey = SESSION_KEY_PREFIX + sessionId;
        // 格式化时间戳（用于对话顺序排序，便于后续上下文拼接）
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // 拼接对话内容（格式：时间|角色|消息，便于前端展示与上下文提取）
        String userContent = time + "|用户|" + userMessage;
        String botContent = time + "|机器人|" + botResponse;
        // 存储到Redis（采用List结构，按顺序追加，保证对话顺序）
        redisTemplate.opsForList().rightPush(sessionKey, userContent);
        redisTemplate.opsForList().rightPush(sessionKey, botContent);
        // 刷新会话过期时间（用户每发送一条消息，重置过期时间）
        redisTemplate.expire(sessionKey, sessionExpireTime, TimeUnit.SECONDS);
    }

    /**
     * 提取会话历史，拼接为上下文（用于多轮对话，传递给模型）
     *
     * @param sessionId 会话ID
     * @return 拼接后的上下文字符串（精简处理，避免超出模型上下文限制）
     */
    public String getContext(String sessionId) {
        String sessionKey = SESSION_KEY_PREFIX + sessionId;
        // 获取会话中的所有对话记录（从Redis中读取List）
        List<String> messageList = redisTemplate.opsForList().range(sessionKey, 0, -1);
        if (messageList == null || messageList.isEmpty()) {
            return ""; // 无历史对话，返回空上下文
        }
        // 拼接上下文（用换行分隔，便于模型识别对话顺序）
        return String.join("\n", messageList) + "\n用户：";
    }

    /**
     * 获取会话历史记录（用于前端展示历史对话）
     *
     * @param sessionId 会话ID
     * @return 会话历史列表（包含所有用户与机器人的对话）
     */
    public List<String> getSessionHistory(String sessionId) {
        String sessionKey = SESSION_KEY_PREFIX + sessionId;
        // 若会话不存在，返回空列表，避免空指针异常
        return redisTemplate.opsForList().range(sessionKey, 0, -1) == null ?
                new ArrayList<>() : redisTemplate.opsForList().range(sessionKey, 0, -1);
    }
}