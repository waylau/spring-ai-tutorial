package com.waylau.spring.ai.multimodal.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.content.Media;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;

/**
 * MultimodalService 多模态服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/18
 **/
@Service
public class MultimodalService {

    private final ChatClient chatClient;

    public MultimodalService(OllamaChatModel ollamaChatModel) {
        this.chatClient = ChatClient.builder(ollamaChatModel).build();
    }

    /**
     * 图像描述生成：输入图片URL，返回文本描述
     */
    public String imageToText(String imageUrl) {
        try {
            // 1. 下载图片
            URL url = URI.create(imageUrl).toURL();
            byte[] imageBytes = url.openStream().readAllBytes();

            // 2. 封装成 Resource（Spring AI 只认这个）
            ByteArrayResource resource = new ByteArrayResource(imageBytes);

            // 3. 官方正确构造 Media
            Media media = new Media(
                    MimeTypeUtils.IMAGE_JPEG,
                    resource
            );

            // 4. 调用（Spring AI + Ollama 唯一正确格式）
            return chatClient.prompt()
                    .user(userSpec -> userSpec
                            .text("详细描述这张图片的内容")
                            .media(media)
                    )
                    .call()
                    .content();

        } catch (Exception e) {
            throw new RuntimeException("图片处理失败：" + e.getMessage(), e);
        }
    }

    public String ocrExtract(String imageUrl) {
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text("提取图片中所有文字，原样返回，不要解释")
                        .media(new Media(MimeTypeUtils.IMAGE_PNG, URI.create(imageUrl))) // media
                )
                .call()
                .content();
    }

    public String imageToTextLocal(File imageFile) {
        try {
            return chatClient.prompt()
                    .user(userSpec -> userSpec
                            .text("详细描述图片")
                            .media(new Media(MimeTypeUtils.IMAGE_PNG, new FileSystemResource(imageFile))) // media
                    )
                    .call()
                    .content();

        } catch (Exception e) {
            throw new RuntimeException("图片处理失败：" + e.getMessage(), e);
        }
    }

    public String multimodalChat(String message, String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            // 纯文本对话
            return chatClient.prompt().user(message).call().content();
        } else {
            // 图文对话
            try {
                // 1. 下载图片
                URL url = URI.create(imageUrl).toURL();
                byte[] imageBytes = url.openStream().readAllBytes();

                // 2. 封装成 Resource（Spring AI 只认这个）
                ByteArrayResource resource = new ByteArrayResource(imageBytes);

                // 3. 官方正确构造 Media
                Media media = new Media(
                        MimeTypeUtils.IMAGE_JPEG,
                        resource
                );

                // 4. 调用（Spring AI + Ollama 唯一正确格式）
                return chatClient.prompt()
                        .user(userSpec -> userSpec
                                .text(message)
                                .media(media)
                        )
                        .call()
                        .content();

            } catch (Exception e) {
                throw new RuntimeException("图片处理失败：" + e.getMessage(), e);
            }
        }
    }
}