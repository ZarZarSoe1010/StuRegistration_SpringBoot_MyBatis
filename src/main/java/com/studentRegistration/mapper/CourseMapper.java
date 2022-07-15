package com.studentRegistration.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.studentRegistration.model.Course;

@Mapper
public interface CourseMapper {
    @Insert("insert into course(cid,name) values(#{cid},#{name})")
    void insertCourse(Course course);
    
    @Select("select * from course")
    List<Course> selectAllCourse();
    
}
