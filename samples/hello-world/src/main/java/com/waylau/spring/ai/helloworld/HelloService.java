package com.waylau.spring.ai.helloworld;

import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * HelloService Hello服务
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/08
 **/
@Service
public class HelloService {
    // 注入AI聊天模型（Spring AI自动配置）
    @Autowired
    private ZhiPuAiChatModel chatModel;

    // 调用ZhiPu AI模型，生成Hello World响应
    public String sayHello() {
        // 定义提示词，请求模型生成相关内容
        String prompt = "结合Spring AI框架，请用简洁的语言输出表达对AI开发的期待。";

        // 调用客户端方法，获取模型响应
        return chatModel.call(prompt);
    }
}
