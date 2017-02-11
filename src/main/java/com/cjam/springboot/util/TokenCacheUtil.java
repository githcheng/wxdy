package com.cjam.springboot.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjam.springboot.entity.Token;
import com.cjam.springboot.service.CoreService;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Created by jam on 2017/1/2.
 */
public class TokenCacheUtil {

    private static final String appid = Token.appid;
    private static final String secret = Token.secret;
    private static final String API_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
    private static final String TOKEN = "TOKEN";


    private final static Logger logger = LoggerFactory.getLogger(CoreService.class);


    /**
     * getToken
     * @return
     */
    public static Token getToken(){
        try {
            Optional<Token> tokenOptional = cache.get(TOKEN);
            if (tokenOptional.isPresent()){
                return tokenOptional.get();
            }
            logger.error("getToken by http fail!!!");
        } catch (ExecutionException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * accessToken
     * @return
     */
    public static String getAccessToken(){
        Token token = getToken();
        System.out.println(JSON.toJSON(token));
        return token.getAccessToken();
    }


    /**
     * guava 本地缓存
     */
    private static LoadingCache<String, Optional<Token>> cache = CacheBuilder.newBuilder()
            .maximumSize(4)
            .expireAfterWrite(7001, TimeUnit.SECONDS) // token 缓存时间 7200, 这边缩短一定时间确保正确
            .build(
                    new CacheLoader<String, Optional<Token>>() {
                        public Optional<Token> load(String key) throws Exception {
                            Token token = getTokenByhttp();
                            if (token == null){
                                logger.error("http fail !!!");
                                return Optional.absent();
                            }
                            return Optional.of(token);
                        }
                    }
            );


    /**
     * by http acquire Token
     * @return
     */
    private static Token getTokenByhttp() {
        int retry = 3;
        JSONObject res = null;
        while (retry-- > 0){
            res = NetClient.postJSONResponse(API_URL);
            if (res != null){
                Token token = JSON.toJavaObject(res, Token.class);
                return token;
            }
        }
        return null;
    }

    /**
     * {"accessToken":
     * "VXUxVFeaOo0zLt6wFK-TNNDDzo5B8yr2FkzMWnQrfMHskm93-wq4yrG2L0B8600V6FC7oVkhh0rCwIDiOdnWFxJyvN6b4pxuYNIPGvv0JQB8Nm2CqV8y-YzjzljpKDeYMLJhACABAY"
     * ,"expiresIn":7200
     * }
     * @param args
     */
    public static void main(String[] args) {
        Token token = getToken();
        System.out.println(JSON.toJSON(token));
    }

}
