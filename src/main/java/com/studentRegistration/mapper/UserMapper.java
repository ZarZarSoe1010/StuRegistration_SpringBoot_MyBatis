package com.studentRegistration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.studentRegistration.model.User;

@Mapper
public interface UserMapper {
    
    @Insert("insert into user(uid,name,email,password,userRole) values(#{uid},#{name},#{email},#{password},#{userRole})")
    void insertUser(User user);
    
    @Update("update user set name=#{name},email=#{email},password=#{password},userRole=#{userRole} where uid=#{uid}")
    void updateUser(User user);

    @Delete("delete from user where uid=#{uid}")
    void deleteUser(String uid);

    @Select ("select * from user")
    List<User>selectAllUser();
    
    @Select ("select * from user where uid=#{uid}")
    User selectOneUser(String uid);

    @Select("select * from user where uid=#{uid} or name=#{name}")
    List<User> selectByFilter(String uid,String name);

    
}
