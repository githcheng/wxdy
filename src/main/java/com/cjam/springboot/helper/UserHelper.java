package com.cjam.springboot.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjam.springboot.entity.WeixinUserInfo;
import com.cjam.springboot.util.MenuManager;
import com.cjam.springboot.util.NetClient;
import com.cjam.springboot.util.TokenCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jam on 2017/1/2.
 */
public class UserHelper {
    private static Logger logger = LoggerFactory.getLogger(MenuManager.class);

    //  获取user info（POST） （次/天）
    public static  String user_info_url_format = "https://api.weixin.qq.com/cgi-bin/user/info?&lang=zh_CN&access_token=%s&openid=%s";

    /**
     * 获取用户信息
     * @param openId
     * @return
     */
    public WeixinUserInfo getUserInfo(String openId){
        String accessToken = TokenCacheUtil.getAccessToken();

        String user_info_url = String.format(user_info_url_format, accessToken, openId);
        JSONObject userObj = NetClient.getJSONResponse(user_info_url);
        WeixinUserInfo weixinUserInfo = JSONObject.toJavaObject(userObj, WeixinUserInfo.class);
        return weixinUserInfo;
    }

    public static void main(String[] args) {
        UserHelper userHelper = new UserHelper();
        WeixinUserInfo userInfo = userHelper.getUserInfo("o9vUiv1o5-FyBMO3DK1YZygkuKQc");
        System.out.println(JSON.toJSON(userInfo));
    }
}
