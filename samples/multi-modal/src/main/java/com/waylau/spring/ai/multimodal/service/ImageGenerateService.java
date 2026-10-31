package com.waylau.spring.ai.multimodal.service;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;

/**
 * ImageGenerateService 图像生成服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/18
 **/
@Service
public class ImageGenerateService {

    private final ImageModel imageModel;

    public ImageGenerateService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    /**
     * 文本生成图片
     */
    public String textToImage(String prompt) {
        ImagePrompt imagePrompt = new ImagePrompt(prompt);
        ImageResponse response = imageModel.call(imagePrompt);
        return response.getResult().getOutput().getUrl();
    }
}