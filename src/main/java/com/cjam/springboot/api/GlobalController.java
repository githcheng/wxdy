package com.cjam.springboot.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjam.springboot.util.GlobalDictUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjam.springboot.appEntity.Student;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by jam on 2016/12/22.
 */
@Controller
@RequestMapping("/global")
public class GlobalController {

    private final static Logger logger = LoggerFactory.getLogger(GlobalController.class);

    @RequestMapping(value="/set",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    JSONObject feedback(HttpServletRequest request,
                        HttpServletResponse response) {

        Map<String, String[]> parameterMap = request.getParameterMap();

        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, String[]> next = iterator.next();
            String key = next.getKey();
            String[] value = next.getValue();
            if (value != null && value.length > 0){
                GlobalDictUtil.put(key,value[0]);
            }
        }
        logger.info("dict:{}", JSON.toJSON(GlobalDictUtil.dict));
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("data", JSON.toJSON(GlobalDictUtil.dict));
        return res;
    }

    public static void main(String[] args) {
        Student user = new Student();
        System.out.println(JSON.toJSON(user));
    }
}
