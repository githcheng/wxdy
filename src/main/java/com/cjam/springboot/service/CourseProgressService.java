package com.cjam.springboot.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjam.springboot.appEntity.BizException;
import com.cjam.springboot.appEntity.CourseProgress;
import com.cjam.springboot.mapper.CourseProgressDAO;

/**
 * Created by jam on 2017/2/9.
 */
@Service
public class CourseProgressService {

    @Autowired
    private CourseProgressDAO courseProgressDAO;

    public List<CourseProgress> getCourseLogByStudentId(long studentId) throws BizException {
        if (studentId <= 0L){
            throw new BizException("用户ID不存在");
        }

        List<CourseProgress> courseProgressList = courseProgressDAO.getCourseProgressByStudentId(studentId);

        if (CollectionUtils.isEmpty(courseProgressList)){
            throw new BizException("您最近没有课程计划");
        }
        return courseProgressList;
    }

}
