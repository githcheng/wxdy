package com.cjam.springboot.entity.offline;

/**
 * Created by jam on 2017/1/2.
 */
public class ViewButton extends Button{
    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
