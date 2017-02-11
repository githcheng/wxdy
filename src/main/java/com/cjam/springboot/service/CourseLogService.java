package com.cjam.springboot.service;

import java.util.List;

import com.cjam.springboot.appEntity.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjam.springboot.appEntity.BizException;
import com.cjam.springboot.appEntity.CourseLog;
import com.cjam.springboot.mapper.CourseLogDAO;

/**
 * Created by jam on 2017/2/9.
 */
@Service
public class CourseLogService {

    @Autowired
    private CourseLogDAO courseLogDAO;

    public List<CourseLog> getCourseLogByStudentId(long studentId) throws BizException {
        if (studentId <= 0L){
            throw new BizException("用户ID不存在");
        }

        List<CourseLog> courseLogList = courseLogDAO.getCourseLogByStudentId(studentId);

        if (CollectionUtils.isEmpty(courseLogList)){
            throw new BizException("您最近没有要学习的课程");
        }
        return courseLogList;
    }

    public List<CourseLog> getCourseLogByTeacherId(long teacherId) throws BizException {
        if (teacherId <= 0L){
            throw new BizException("用户ID不存在");
        }

        List<CourseLog> courseLogList = courseLogDAO.getCourseLogByTeacherId(teacherId);

        if (CollectionUtils.isEmpty(courseLogList)){
            throw new BizException("您最近没有课程安排");
        }
        return courseLogList;
    }

    public List<CourseLog> getCourseLogList(User user) throws BizException {

        int type = user.getType();

        if (User.STUDENT_TYPE == type){
            return getCourseLogByStudentId(user.getId());
        } else if (User.TEACHER_TYPE == type){
            return getCourseLogByTeacherId(user.getId());
        }
        throw new BizException("没有权限");
    }


    public CourseLog getCourseLogById(long id) throws BizException {
        if (id <= 0L){
            throw new BizException("记录ID不存在");
        }

        CourseLog courseLog = courseLogDAO.getCourseLogById(id);

        if (courseLog == null){
            throw new BizException("记录不存在");
        }
        return courseLog;
    }


    public int insert(CourseLog courseLog) throws BizException {

        long curTime = System.currentTimeMillis();
        courseLog.setCreateTime(curTime);
        courseLog.setUpdateTime(curTime);

        int res = courseLogDAO.insert(courseLog);
        if (res <= 0){
            throw new BizException("CourseLog 更新失败");
        }
        return res;
    }
}
