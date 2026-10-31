package com.waylau.spring.ai.enterpriseai.exception;

/**
 * AIException AI异常
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
public class AIException extends RuntimeException {

    public AIException(String message) {
        super(message);
    }

    public AIException(String message, Throwable cause) {
        super(message, cause);
    }
}