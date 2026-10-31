package com.waylau.spring.ai.multimodal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ImageGenerationServiceTest ImageGenerationService Test
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/18
 **/
@SpringBootTest
public class ImageGenerationServiceTest {

    @Autowired
    private ImageGenerateService imageGenerateService;

    @Test
    void testGenerate() {
        String url = imageGenerateService.textToImage("一只可爱的橘猫在花园里");
        System.out.println("图片地址：" + url);
    }
}