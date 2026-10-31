package com.waylau.spring.ai.prompttemplate.dto;

import java.util.List;

/**
 * MarketingRequest 营销文案生成请求参数
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
public class MarketingRequest {

    // 文风不能为空
    private String tone;

    // 目标受众不能为空
    private String audience;

    // 字数限制不能为空
    private Integer wordCount;

    // 产品名称不能为空
    private String productName;

    // 核心卖点不能为空
    private List<String> features;

    // --- 以下是标准的 Getter、Setter、构造方法 ---
    // 在实际项目中，你可以直接使用 Lombok 的 @Data 注解替代这些样板代码

    public MarketingRequest() {
    }

    public MarketingRequest(String tone, String audience, Integer wordCount, String productName, List<String> features) {
        this.tone = tone;
        this.audience = audience;
        this.wordCount = wordCount;
        this.productName = productName;
        this.features = features;
    }

    public String getTone() {
        return tone;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }
}
