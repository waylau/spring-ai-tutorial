package com.waylau.spring.ai.contentprocesschain;

import com.waylau.spring.ai.contentprocesschain.service.ContentProcessChainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ContentProcessChainApplicationTests {
    @Autowired
    private ContentProcessChainService contentProcessChainService;

    @Test
    void contextLoads() {
        contentProcessChainService.processFeedback(
                "我昨天刚收到你们寄来的那个破微波炉，外包装都瘪了，插上电根本不加热，按钮还卡住了，赶紧给我换一个新的，否则我就要投诉到消费者协会了！"
        );
    }

}
