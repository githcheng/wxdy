package com.cjam.springboot.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjam.springboot.appEntity.BizException;
import com.cjam.springboot.appEntity.CourseProgress;
import com.cjam.springboot.appEntity.Student;
import com.cjam.springboot.appEntity.User;
import com.cjam.springboot.service.CourseProgressService;
import com.cjam.springboot.service.UserService;
import com.cjam.springboot.util.TimeUtil;

/**
 * Created by jam on 2016/12/22.
 */
@Controller
@RequestMapping("/course")
public class CourseProgressController {

    private final static Logger logger = LoggerFactory.getLogger(CourseProgressController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private CourseProgressService courseProgressService;

    @RequestMapping(value="/getCourseProgressList",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    JSONObject feedback(HttpServletRequest request,
                        HttpServletResponse response) {

        String openid = request.getParameter("openid");

        JSONObject res = new JSONObject();
        res.put("code",0);

        User user = null;
        try {

            logger.info("openid:{}",openid);
            user = userService.findByOpenId(openid);
            List<CourseProgress> courseLogList = courseProgressService.getCourseLogByStudentId(user.getId());
            JSONArray jsonList = (JSONArray)JSON.toJSON(courseLogList);
            int size = jsonList.size();
            for (int i=0;i<size;i++){
                JSONObject obj = jsonList.getJSONObject(i);
                obj.put("createTime",TimeUtil.getFormatTime(obj.getLong("createTime")));
                obj.put("updateTime",TimeUtil.getFormatTime(obj.getLong("updateTime")));
            }

            res.put("data",jsonList);
        } catch (BizException e) {
            logger.error(e.getMessage(),e);
            res.put("code",1);
            res.put("msg", e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            res.put("code",1);
            res.put("msg", "服务异常");
        } finally {
            return res;
        }
    }

    public static void main(String[] args) {
        Student user = new Student();
        System.out.println(JSON.toJSON(user));
    }
}
