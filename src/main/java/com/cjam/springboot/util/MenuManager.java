package com.cjam.springboot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjam.springboot.entity.offline.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jam on 2017/1/2.
 * 菜单管理
 */
public class MenuManager {

    private static Logger logger = LoggerFactory.getLogger(MenuManager.class);

    // 菜单创建（POST） （次/天）
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

    public static void main(String[] args) {

        menu_create_url = menu_create_url + TokenCacheUtil.getAccessToken();
        logger.info("menu_create_url:{}",menu_create_url);
        Menu menu = getMenu();
        System.out.println(JSON.toJSON(menu));
        try {
            String res = NetClient.postResponse(menu_create_url, JSON.toJSON(menu));
            JSONObject resObj = JSONObject.parseObject(res);
            System.out.println(JSON.toJSON(resObj));
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("天气预报");
        btn11.setType("click");
        btn11.setKey("11");

        CommonButton btn12 = new CommonButton();
        btn12.setName("公交查询");
        btn12.setType("click");
        btn12.setKey("12");

        CommonButton btn13 = new CommonButton();
        btn13.setName("周边搜索");
        btn13.setType("click");
        btn13.setKey("13");

        CommonButton btn14 = new CommonButton();
        btn14.setName("历史上的今天");
        btn14.setType("click");
        btn14.setKey("14");

        CommonButton btn21 = new CommonButton();
        btn21.setName("歌曲点播");
        btn21.setType("click");
        btn21.setKey("21");

        CommonButton btn22 = new CommonButton();
        btn22.setName("经典游戏");
        btn22.setType("click");
        btn22.setKey("22");

        CommonButton btn23 = new CommonButton();
        btn23.setName("美女电台");
        btn23.setType("click");
        btn23.setKey("23");

        CommonButton btn24 = new CommonButton();
        btn24.setName("人脸识别");
        btn24.setType("click");
        btn24.setKey("24");

        CommonButton btn25 = new CommonButton();
        btn25.setName("聊天唠嗑");
        btn25.setType("click");
        btn25.setKey("25");

        ViewButton btn31 = new ViewButton();
        btn31.setName("Q友圈");
        btn31.setType("view");
        btn31.setUrl("http://52hts.club/htmlView/hello");

        ViewButton btn32 = new ViewButton();
        btn32.setName("电影排行榜");
        btn32.setType("view");
        btn32.setUrl("http://www.52hts.club/htmlView/hello");

        ViewButton btn33 = new ViewButton();
        btn33.setName("幽默笑话");
        btn33.setType("view");
        btn33.setUrl("https://weui.io");


        /**
         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("生活助手");
        //一级下有4个子菜单
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12, btn13, btn14 });


        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("休闲驿站");
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23, btn24, btn25 });


        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("更多体验");
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });


        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }
}
