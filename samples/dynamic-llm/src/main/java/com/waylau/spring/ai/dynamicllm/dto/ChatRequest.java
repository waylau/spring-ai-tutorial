package com.waylau.spring.ai.dynamicllm.dto;

/**
 * ChatRequest 多模型对话请求体
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
public class ChatRequest {
    private String provider;
    private String message;

    // 必须提供无参构造器，供 Jackson 反序列化使用
    public ChatRequest() {
    }

    // 可选：全参构造器
    public ChatRequest(String provider, String message) {
        this.provider = provider;
        this.message = message;
    }

    // Getter 和 Setter 方法（必须有，Jackson 依赖它们注入数据）
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
