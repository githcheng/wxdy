package com.cjam.springboot.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cjam.springboot.appEntity.User;

import java.util.List;

/**
 * Created by jam on 2017/2/8.
 */
@Mapper
public interface UserDAO {


    @Select("SELECT nick_name as nickName FROM user WHERE name = #{name}")
    User findByName(@Param("name") String name);

    @Select("SELECT id,type,status, identity_number as identityNumber,open_id as openId, name," +
            "nick_name as nickName, phone ,head_url as headUrl  FROM user WHERE open_id = #{openId}")
    User findByOpenId(@Param("openId") String openId);


    @Select("SELECT id,type,status, identity_number as identityNumber,open_id as openId, name," +
            "nick_name as nickName, phone ,head_url as headUrl  FROM user")
    List<User> getAllUser();

}

