package com.waylau.spring.ai.copygenerator.controller;

import com.waylau.spring.ai.copygenerator.service.CopyGeneratorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * CopyGeneratorController 智能文案生成控制器
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/15
 **/
@RestController
@RequestMapping("/ai/copy")
public class CopyGeneratorController {
    private final CopyGeneratorService service;

    public CopyGeneratorController(CopyGeneratorService service) {
        this.service = service;
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(
            @RequestParam String type,
            @RequestParam String keyword,
            @RequestParam String style) {
        return service.generateStream(type, keyword, style);
    }
}