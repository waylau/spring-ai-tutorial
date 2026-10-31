package com.waylau.spring.ai.enterpriseai.controller;

import com.waylau.spring.ai.enterpriseai.bean.R;
import com.waylau.spring.ai.enterpriseai.service.OfficeAssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * OfficeAssistantController 智能办公助手控制器
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
@RestController
@RequestMapping("/ai/office")
@Tag(name = "智能办公助手")
@PreAuthorize("isAuthenticated()")
public class OfficeAssistantController {

    private final OfficeAssistantService assistantService;

    // 构造函数注入
    public OfficeAssistantController(OfficeAssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping("/chat")
    @Operation(summary = "智能对话")
    public R<String> chat(
            @RequestParam String userId,
            @RequestParam String sessionId,
            @RequestParam String message) {
        return R.ok(assistantService.chat(userId, sessionId, message));
    }

    @GetMapping("/email")
    @Operation(summary = "生成邮件")
    public R<String> email(@RequestParam String content) {
        return R.ok(assistantService.generateEmail(content));
    }
}