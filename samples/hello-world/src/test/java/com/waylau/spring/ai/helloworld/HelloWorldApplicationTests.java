package com.waylau.spring.ai.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelloWorldApplicationTests {

    @Autowired
    private HelloService helloService;

    @Test
    void testHello() {
        // 调用sayHello方法，获取响应并打印
        String response = helloService.sayHello();

        System.out.println("AI响应：" + response);
    }

}
