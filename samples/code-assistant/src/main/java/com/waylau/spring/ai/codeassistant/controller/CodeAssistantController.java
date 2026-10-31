package com.waylau.spring.ai.codeassistant.controller;

import com.waylau.spring.ai.codeassistant.service.CodeAssistantService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * CodeAssistantController 代码助手控制器
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/15
 **/
@RestController
@RequestMapping("/ai/code")
public class CodeAssistantController {
    private final CodeAssistantService service;

    public CodeAssistantController(CodeAssistantService service) {
        this.service = service;
    }

    @PostMapping("/generate")
    public String generate(
            @RequestParam String language,
            @RequestParam String requirement) {
        return service.generateCode(language, requirement);
    }

    @PostMapping("/optimize")
    public String optimize(@RequestParam String code) {
        return service.optimizeCode(code);
    }
}
