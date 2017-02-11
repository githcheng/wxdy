package com.cjam.springboot.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cjam.springboot.appEntity.CourseLog;
import com.cjam.springboot.appEntity.User;
import com.cjam.springboot.service.CourseLogService;
import com.cjam.springboot.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.cjam.springboot.appEntity.BizException;
import com.cjam.springboot.appEntity.FeedbackLog;
import com.cjam.springboot.service.FeedbackLogService;

/**
 * Created by jam on 2016/12/22.
 */
@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    private final static Logger logger = LoggerFactory.getLogger(FeedBackController.class);

    @Autowired
    private FeedbackLogService feedbackLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseLogService courseLogService;

    @RequestMapping(value="/do",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    JSONObject feedback(HttpServletRequest request,
                        HttpServletResponse response) {

        String courseLogId = request.getParameter("courselogid");
        String openid = request.getParameter("openid");
        String score = request.getParameter("score");
        String isCommon = request.getParameter("isCommon");
        String content = request.getParameter("content");
        logger.info("courseLogId:{}, content:{},openid:{},isCommon:{},score:{}",courseLogId,content,openid,isCommon,score);


        JSONObject res = new JSONObject();

        if (StringUtils.isEmpty(content)){
            res.put("code",1);
            res.put("msg", "内容为空");
            return res;
        }

        try {

            User user = userService.findByOpenId(openid);
            FeedbackLog feedbackLog = new FeedbackLog(user.getId(),content,
                    StringUtils.isNumeric(courseLogId) ? Long.valueOf(courseLogId) : 0,-1);

            if (StringUtils.isBlank(isCommon)){
                feedbackLog.setType(user.getType());
                if (StringUtils.isEmpty(courseLogId)){
                    res.put("code",1);
                    res.put("msg", "courselogid为空");
                    return res;
                }

                if (user.getType() == User.STUDENT_TYPE){
                    if (StringUtils.isEmpty(score)){
                        // 学生课程反馈
                        res.put("code",1);
                        res.put("msg", "score为空");
                        return res;
                    }
                    // 学生课程反馈 有对课程的打分
                    feedbackLog.setScore(Integer.valueOf(score));
                }

                CourseLog courseLog = courseLogService.getCourseLogById(Long.valueOf(courseLogId));
                if (user.getId() != courseLog.getStudentId() && user.getId() != courseLog.getTeacherId()){
                    // 学生课程反馈
                    res.put("code",1);
                    res.put("msg", "用户只能反馈与自己相关的课程");
                    return res;
                }
            } else {
                // 普通反馈
                feedbackLog.setType(0);
            }

            feedbackLogService.addLog(feedbackLog);
            res.put("code",0);
            res.put("msg", "ok");
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
}
