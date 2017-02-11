package com.cjam.springboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by jam on 2017/2/8.
 */
@Mapper
public interface FeedbackLogDAO {


    @Insert("INSERT INTO feedback_log(type,user_id,course_log_id, score,content,create_time,update_time) " +
            "VALUES(#{type},#{userId},#{courseLogId},#{score},#{content},#{createTime}, #{updateTime})")
    int insert(
                @Param("type") Integer type,
                @Param("userId") long userId,
               @Param("courseLogId") long courseLogId,
               @Param("score") Integer score,
               @Param("content") String content,
               @Param("createTime") long createTime,
               @Param("updateTime") long updateTime
               );
}
