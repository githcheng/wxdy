package com.cjam.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.cjam.springboot.appEntity.Course;

/**
 * Created by jam on 2017/2/8.
 */
@Mapper
public interface CourseDAO {


    @Select("SELECT id, type, name, create_time as createTime, " +
            "update_time as updateTime, desc_msg as descMsg FROM course")
    List<Course> getAllCourse();



}

