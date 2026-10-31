package com.waylau.spring.ai.multimodal.controller;

import com.waylau.spring.ai.multimodal.service.ImageGenerateService;
import com.waylau.spring.ai.multimodal.service.MultimodalService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * MultimodalController 多模态Controller
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/18
 **/
@RestController
@RequestMapping("/ai/multimodal")
public class MultimodalController {

    private final MultimodalService multimodalService;
    private final ImageGenerateService imageGenerateService;

    public MultimodalController(MultimodalService multimodalService,
                                ImageGenerateService imageGenerateService) {
        this.multimodalService = multimodalService;
        this.imageGenerateService = imageGenerateService;
    }

    /**
     * 多模态对话（文字+图片）
     */
    @PostMapping("/chat")
    public Map<String, String> chat(
            @RequestParam(required = false) String message,
            @RequestParam(required = false) String imageUrl) {
        String reply = multimodalService.multimodalChat(message, imageUrl);
        return Map.of("reply", reply);
    }

    /**
     * 文生图
     */
    @GetMapping("/image/generate")
    public Map<String, String> generateImage(@RequestParam String prompt) {
        String imageUrl = imageGenerateService.textToImage(prompt);
        return Map.of("imageUrl", imageUrl);
    }

}
