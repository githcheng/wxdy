package com.cjam.springboot.entity;

/**
 * Created by jam on 2017/1/2.
 */
public class TextMessage extends BaseMessage{

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
