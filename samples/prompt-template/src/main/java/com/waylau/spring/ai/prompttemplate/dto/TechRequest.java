package com.waylau.spring.ai.prompttemplate.dto;

/**
 * TechRequest 技术摘要生成请求参数
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
public class TechRequest {

    private String domain;

    private String rawText;

    // --- 以下是标准的 Getter、Setter、构造方法 ---

    public TechRequest() {
    }

    public TechRequest(String domain, String rawText) {
        this.domain = domain;
        this.rawText = rawText;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }
}
