package com.waylau.spring.ai.assistanttools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * AssistantTools 工具类
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/14
 **/
@Component
public class AssistantTools {

    @Tool(description = "计算两个数字的加减乘除结果。当用户提问数学计算相关问题时调用此工具。")
    public String calculate(
            @ToolParam(description = "第一个数字") double num1,
            @ToolParam(description = "运算符，只能是加、减、乘、除") String operator,
            @ToolParam(description = "第二个数字") double num2) {

        return switch (operator) {
            case "加" -> String.valueOf(num1 + num2);
            case "减" -> String.valueOf(num1 - num2);
            case "乘" -> String.valueOf(num1 * num2);
            case "除" -> num2 == 0 ? "错误：除数不能为0" : String.valueOf(num1 / num2);
            default -> "错误：不支持的运算符";
        };
    }

    @Tool(description = "获取指定城市的当前天气温度。当用户询问天气、温度、是否需要穿外套时调用此工具。")
    public String getWeather(@ToolParam(description = "城市名称") String city) {
        return switch (city) {
            case "北京" -> "晴天，温度15摄氏度，微风";
            case "上海" -> "阴天，温度22摄氏度，有雨";
            case "广州" -> "暴雨，温度28摄氏度";
            default -> city + "的天气数据暂未接入";
        };
    }
}