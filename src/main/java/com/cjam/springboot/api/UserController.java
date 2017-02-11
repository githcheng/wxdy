package com.cjam.springboot.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.cjam.springboot.appEntity.Student;
import com.cjam.springboot.util.GlobalDictUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by jam on 2016/12/22.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value="/getUserInfo",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    JSONObject feedback(HttpServletRequest request,
                        HttpServletResponse response) {

        String openid = request.getParameter("openid");


        logger.info("openid:{}",openid);
        JSONObject res = new JSONObject();
        if (StringUtils.isEmpty(openid)){
            res.put("code",1);
            res.put("msg", "openid为空");
            return res;
        }
        Student user = new Student();
        user.setType(Integer.valueOf(GlobalDictUtil.get("type")));
        res.put("code",0);
        res.put("user", JSON.toJSON(user));
        return res;
    }

    public static void main(String[] args) {
        Student user = new Student();
        System.out.println(JSON.toJSON(user));
    }
}
