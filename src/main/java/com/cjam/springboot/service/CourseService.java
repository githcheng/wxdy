package com.cjam.springboot.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjam.springboot.appEntity.BizException;
import com.cjam.springboot.appEntity.Course;
import com.cjam.springboot.mapper.CourseDAO;

/**
 * Created by jam on 2017/2/9.
 */
@Service
public class CourseService {

    @Autowired
    private CourseDAO courseDAO;


    public List<Course> getAllCourse() throws BizException {

        List<Course> allCourse = courseDAO.getAllCourse();

        if (CollectionUtils.isEmpty(allCourse)){
            throw new BizException("暂无课程");
        }
        return allCourse;
    }
}
