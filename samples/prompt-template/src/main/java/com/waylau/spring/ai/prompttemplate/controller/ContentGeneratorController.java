package com.waylau.spring.ai.prompttemplate.controller;

import com.waylau.spring.ai.prompttemplate.dto.MarketingRequest;
import com.waylau.spring.ai.prompttemplate.dto.TechRequest;
import com.waylau.spring.ai.prompttemplate.service.MultiSceneGeneratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ContentGeneratorController 内容生成控制器
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
@RestController
@RequestMapping("/api/generate")
public class ContentGeneratorController {
    private final MultiSceneGeneratorService generatorService;

    public ContentGeneratorController(MultiSceneGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @PostMapping("/marketing")
    public String generateMarketingCopy(@RequestBody MarketingRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("tone", request.getTone());
        params.put("audience", request.getAudience());
        params.put("word_count", request.getWordCount());
        params.put("product_name", request.getProductName());
        params.put("features", String.join("、", request.getFeatures()));

        // 指定场景名为 "marketing"，对应 marketing.st
        return generatorService.generate("marketing", params);
    }

    @PostMapping("/tech-summary")
    public String generateTechSummary(@RequestBody TechRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("domain", request.getDomain());
        params.put("raw_text", request.getRawText());

        // 指定场景名为 "tech-summary"
        return generatorService.generate("tech-summary", params);
    }
}