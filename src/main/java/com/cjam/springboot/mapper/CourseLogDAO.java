package com.cjam.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cjam.springboot.appEntity.CourseLog;

/**
 * Created by jam on 2017/2/8.
 */
@Mapper
public interface CourseLogDAO {


    @Select("SELECT id, type, name, status, student_id as studentId," +
            " class_id as classId, course_id as courseId, teacher_id as teacherId," +
            "teacher_name as teacherName, student_name as studentName,class_name as className," +
            "begin_time as beginTime, end_time as endTime, create_time as createTime, " +
            "update_time as updateTime, desc_msg as descMsg FROM course_log WHERE student_id = #{studentId}")
    List<CourseLog> getCourseLogByStudentId(@Param("studentId") long studentId);


    @Select("SELECT id, type, name, status, student_id as studentId," +
            " class_id as classId, course_id as courseId, teacher_id as teacherId, " +
            "teacher_name as teacherName, student_name as studentName,class_name as className," +
            "begin_time as beginTime, end_time as endTime, create_time as createTime, " +
            "update_time as updateTime, desc_msg as descMsg FROM course_log WHERE teacher_id = #{teacherId}")
    List<CourseLog> getCourseLogByTeacherId(@Param("teacherId") long teacherId);


    @Select("SELECT id, type, name, status, student_id as studentId," +
            " class_id as classId, course_id as courseId, teacher_id as teacherId, " +
            "teacher_name as teacherName, student_name as studentName,class_name as className," +
            "begin_time as beginTime, end_time as endTime, create_time as createTime, " +
            "update_time as updateTime, desc_msg as descMsg FROM course_log WHERE id = #{id}")
    CourseLog getCourseLogById(@Param("id")long id);


    @Insert("insert into course_log (type, name, status, student_id, " +
            " class_id , course_id , teacher_id  , " +
            "teacher_name  , student_name  ,class_name  ," +
            "begin_time  , end_time , create_time , " +
            "update_time , desc_msg  ) value(#{item.type},#{item.name},#{item.status},#{item.studentId}," +
            " #{item.classId},#{item.courseId},#{item.teacherId},#{item.teacherName},#{item.studentName},#{item.className}, #{item.beginTime},#{item.endTime},#{item.createTime}," +
            "#{item.updateTime},#{item.descMsg} )")
    int insert(@Param("item")CourseLog courseLog);
}

