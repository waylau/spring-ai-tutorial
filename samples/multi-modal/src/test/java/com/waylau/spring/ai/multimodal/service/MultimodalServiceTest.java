package com.waylau.spring.ai.multimodal.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
/**
 * MultimodalServiceTest MultimodalService Test
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/18
 **/
@SpringBootTest
public class MultimodalServiceTest {

    @Autowired
    private MultimodalService multimodalService;

    /**
     * 测试：图像URL生成描述
     */
    @Test
    void imageToText_WithValidImageUrl_ShouldReturnDescription() {
        // 给定一张公开可访问的图片URL
        String imageUrl = "https://picsum.photos/1024/768";

        // 执行图像描述生成
        String caption = multimodalService.imageToText(imageUrl);

        // 断言结果不为空且长度合理
        assertNotNull(caption);
        assertFalse(caption.isBlank());
        assertTrue(caption.length() > 10);

        // 打印结果便于查看
        System.out.println("图像描述：" + caption);
    }

    /**
     * 测试：本地图片生成描述
     */
    @Test
    void imageToTextLocal_WithLocalFile_ShouldReturnDescription() {
        // 替换为你本地存在的图片路径
        String localImagePath = "D:/test.jpg";
        File imageFile = new File(localImagePath);
        assertTrue(imageFile.exists(), "本地图片不存在");

        String caption = multimodalService.imageToTextLocal(imageFile);

        assertNotNull(caption);
        assertFalse(caption.isBlank());
        System.out.println("本地图像描述：" + caption);
    }

}