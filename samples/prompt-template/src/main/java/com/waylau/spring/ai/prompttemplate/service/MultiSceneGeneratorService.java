package com.waylau.spring.ai.prompttemplate.service;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * MultiSceneGeneratorService 多场景生成
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/09
 **/
@Service
public class MultiSceneGeneratorService {
    private final ResourceLoader resourceLoader;
    private final ChatModel chatModel;

    public MultiSceneGeneratorService(ResourceLoader resourceLoader, ChatModel chatModel) {
        this.resourceLoader = resourceLoader;
        this.chatModel = chatModel;
    }

    /**
     * 多场景文本生成核心方法
     *
     * @param scene  场景标识 (如 "marketing", "tech-summary")
     * @param params 模板替换参数
     * @return LLM生成的文本
     */
    public String generate(String scene, Map<String, Object> params) {
        // 1. 根据场景动态构建模板资源的类路径
        String templatePath = "classpath:prompts/" + scene + ".st";
        Resource resource = resourceLoader.getResource(templatePath);

        // 2. 校验资源是否存在 (使用 resource.exists() 是安全的做法)
        if (!resource.exists()) {
            throw new IllegalArgumentException("未找到对应的场景模板: " + templatePath);
        }

        // 3. 创建 PromptTemplate 实例（直接传入 Resource，底层自动读取）
        PromptTemplate promptTemplate = new PromptTemplate(resource);

        // 4. 将参数Map与模板结合，生成最终的Prompt对象
        Prompt prompt = promptTemplate.create(params);

        // 5. 调用底层ChatModel执行生成
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

}