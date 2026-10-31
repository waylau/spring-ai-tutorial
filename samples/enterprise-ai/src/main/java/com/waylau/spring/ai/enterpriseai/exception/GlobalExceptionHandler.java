package com.waylau.spring.ai.enterpriseai.exception;

import com.waylau.spring.ai.enterpriseai.bean.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.ai.retry.TransientAiException;
/**
 * GlobalExceptionHandler 全局异常处理器
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义AI异常
     */
    @ExceptionHandler(AIException.class)
    public R<?> handleAIException(AIException e) {
        return R.fail("AI服务异常：" + e.getMessage());
    }

    /**
     * Spring AI 内置临时异常
     */
    @ExceptionHandler(TransientAiException.class)
    public R<?> handleTransientAiException(TransientAiException e) {
        return R.fail("AI调用超时，请稍后重试：" + e.getMessage());
    }

    /**
     * Spring AI 内置非临时异常
     */
    @ExceptionHandler(NonTransientAiException.class)
    public R<?> handleNonTransientAiException(NonTransientAiException e) {
        return R.fail("AI模型异常：" + e.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        return R.fail("参数错误：" + msg);
    }

    /**
     * 其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        e.printStackTrace();
        return R.fail("服务器异常：" + e.getMessage());
    }
}