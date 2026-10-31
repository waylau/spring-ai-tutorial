package com.waylau.spring.ai.enterpriseai.bean;

import lombok.Data;

/**
 * R 统一响应结果
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @version 2026/04/19
 **/
@Data
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    public static <T> R<T> fail(String msg) {
        R<T> r = new R<>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
}