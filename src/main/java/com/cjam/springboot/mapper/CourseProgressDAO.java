package com.cjam.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cjam.springboot.appEntity.CourseProgress;

/**
 * Created by jam on 2017/2/8.
 */
@Mapper
public interface CourseProgressDAO {


    @Insert("INSERT INTO course_progress(student_id, student_name,course_id,course_name," +
            "used, total,desc_msg, create_time,update_time) " +
            "VALUES(#{studentId},#{studentName},#{courseId},#{courseName}," +
            "#{used},#{total},#{descMsg},#{createTime}, #{updateTime})")
    int insert(
            @Param("studentId") long studentId,
            @Param("studentName") Integer studentName,
            @Param("courseId") String courseId,
            @Param("studentName") Integer courseName,
            @Param("used") double used,
            @Param("total") double total,
            @Param("descMsg") String descMsg,
            @Param("createTime") long createTime,
            @Param("updateTime") long updateTime
    );

    @Select("SELECT id, student_id as studentId,  student_name as studentName," +
            " course_id as courseId, course_name as courseName," +
            " used, total,status,desc_msg as descMsg, " +
            " create_time as createTime,update_time as updateTime " +
            " FROM course_progress WHERE student_id = #{studentId}")
    List<CourseProgress> getCourseProgressByStudentId(@Param("studentId") long studentId);
}
