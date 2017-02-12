package com.cjam.springboot.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.cjam.springboot.appEntity.*;
import com.cjam.springboot.service.CourseLogService;
import com.cjam.springboot.service.CourseService;
import com.cjam.springboot.service.UserService;
import com.cjam.springboot.util.TimeUtil;

/**
 * Created by jam on 2016/12/22.
 */
@Controller
@RequestMapping("/course")
public class CourseController {

    private final static Logger logger = LoggerFactory.getLogger(CourseController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private CourseLogService courseLogService;

    @Autowired
    private CourseService courseService;

    @RequestMapping(value="/getCourseList",method={RequestMethod.GET, RequestMethod.POST})
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
            List<CourseLog> courseLogList = courseLogService.getCourseLogList(user);
            JSONArray jsonList = (JSONArray)JSON.toJSON(courseLogList);
            int size = jsonList.size();
            for (int i=0;i<size;i++){
                JSONObject obj = jsonList.getJSONObject(i);
                obj.put("beginTime",TimeUtil.getFormatTime(obj.getLong("beginTime")));
                obj.put("endTime",TimeUtil.getFormatTime(obj.getLong("endTime")));
                if(user.getType() == User.STUDENT_TYPE){
                    obj.put("userName",obj.getString("teacherName"));
                } else if (user.getType() == User.TEACHER_TYPE){
                    String studentName = obj.getString("studentName");
                    if (StringUtils.isBlank(studentName)){
                        obj.put("userName",obj.getString("className"));
                    } else {
                        obj.put("userName",studentName);
                    }
                }
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


    @RequestMapping(value="/submitCourseLog",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    JSONObject submitCourseLog(HttpServletRequest request,
                        HttpServletResponse response) {

        String openid = request.getParameter("openid");
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        String teacherId = request.getParameter("teacherId");
        String date = request.getParameter("date");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String descmsg = request.getParameter("descmsg");



        String courseName = request.getParameter("courseName");
        String studentName = request.getParameter("studentName");
        String teacherName = request.getParameter("teacherName");



        JSONObject res = new JSONObject();
        res.put("code",0);

        User user = null;
        try {

            // 检查输入
            submitCourseLogCheck(request);

            User operator = userService.findByOpenId(openid);
            CourseLog courseLog = new CourseLog(courseId, studentId, teacherId, courseName, studentName, teacherName, date, beginTime, endTime, descmsg);
            courseLogService.insert(courseLog);
            res.put("msg","ok");
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



    @RequestMapping(value="/getPickerList",method={RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody
    JSONObject getPickerList(HttpServletRequest request,
                               HttpServletResponse response) {

        String openid = request.getParameter("openid");

        JSONObject res = new JSONObject();
        res.put("code",0);

        try {

            List<User> allUser = userService.getAllUser();
            JSONArray studentList = new JSONArray();
            JSONArray teacherList = new JSONArray();
            for (User user : allUser){
                JSONObject obj = new JSONObject();
                obj.put("id",user.getId());
                obj.put("nickname",user.getNickName());
                if (user.getType() == User.STUDENT_TYPE){
                    studentList.add(obj);
                } else {
                    teacherList.add(obj);
                }
            }

            List<Course> allCourse = courseService.getAllCourse();
            JSONArray courseList = new JSONArray();
            for (Course course : allCourse){
                JSONObject obj = new JSONObject();
                obj.put("id",course.getId());
                obj.put("nickname",course.getName());
                courseList.add(obj);
            }

            res.put("studentList",studentList);
            res.put("teacherList",teacherList);
            res.put("courseList",JSON.toJSON(courseList));
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


    private void submitCourseLogCheck(HttpServletRequest request) throws BizException {
        String openid = request.getParameter("openid");
        String courseId = request.getParameter("courseId");
        String studentId = request.getParameter("studentId");
        String teacherId = request.getParameter("teacherId");
        String date = request.getParameter("date");
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String descmsg = request.getParameter("descmsg");


        String courseName = request.getParameter("courseName");
        String studentName = request.getParameter("studentName");
        String teacherName = request.getParameter("teacherName");

        logger.info("openid:{},courseId:{},studentId:{},teacherId:{}," +
                "date:{},beginTime:{},endTime:{},descmsg:{}",
                openid,courseId,studentId,teacherId,date,beginTime,endTime,descmsg);


        if (!StringUtils.isNumeric(courseId) || Integer.valueOf(courseId) <= 0){
            throw new BizException("请选择正确的课程");
        }
        if (!StringUtils.isNumeric(studentId) || Long.valueOf(studentId) <= 0){
            throw new BizException("请选择正确的学生");
        }
        if (!StringUtils.isNumeric(teacherId) || Long.valueOf(teacherId) <= 0){
            throw new BizException("请选择正确的老师");
        }
        if (StringUtils.isBlank(date)){
            throw new BizException("请选择正确的日期");
        }
        if (StringUtils.isBlank(beginTime)){
            throw new BizException("请选择正确的开始时间");
        }
        if (StringUtils.isBlank(endTime)){
            throw new BizException("请输入正确的结束时间");
        }

        if (StringUtils.isBlank(courseName)){
            throw new BizException("请选择正确的课程名");
        }
        if (StringUtils.isBlank(studentName)){
            throw new BizException("请选择正确的学生名");
        }
        if (StringUtils.isBlank(teacherName)){
            throw new BizException("请选择正确的老师名");
        }

    }


    public static void main(String[] args) {
        Student user = new Student();
        System.out.println(JSON.toJSON(user));
    }
}
