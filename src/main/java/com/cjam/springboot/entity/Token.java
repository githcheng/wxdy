package com.cjam.springboot.entity;

/**
 * Created by jam on 2017/1/2.
 */
public class Token {

    // test wxad142e4cf23cadee
    /**
     * test
     * wxad142e4cf23cadee
     * online
     * wxc36439d8421a99ca
     */
    public static final String appid = "wxad142e4cf23cadee";

    /**
     * test
     * 47365469156170b4b0523ffadbeb104f
     * online
     *
     * ad1a67c1774a5249b779da1af7d7db56
     */
    public static final String secret = "47365469156170b4b0523ffadbeb104f";

    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
