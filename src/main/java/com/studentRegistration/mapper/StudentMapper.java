package com.studentRegistration.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.studentRegistration.model.Student;

@Mapper
public interface StudentMapper {
    @Insert("insert into student(sid,name,dob,gender,phone,education) values(#{sid},#{name},#{dob},#{gender},#{phone},#{education})")
    void insertStudent(Student student);
    
    @Update("update student set name=#{name},dob=#{dob},gender=#{gender},phone=#{phone},education=#{education} where sid=#{sid}")
    void updateStudent(Student sudent);

    @Delete("delete from student where sid=#{sid}")
    void deleteStudent(String sid);

    @Select ("select * from student")
    List<Student>selectAllStudent();
    
    @Select ("select * from student where sid=#{sid}")
    Student selectOneStudent(String sid);

    @Select("select distinct s.sid,s.name,c.name from student s join student_course sc on s.sid=sc.sid join course c on sc.cid=c.cid  where s.sid like #{sid} or s.name like #{name} or c.name like #{course}")
    List<Student> selectStudentListByIdOrNameOrCourse(String sid,String name,String course);
  
    @Insert("insert into student_course(sid,cid) values(#{sid},#{cid})")
    void insertStudent_Course(String sid,String cid);

    @Delete("delete from student_course where sid=#{sid}")
    void deleteStudent_Course(String sid);

    @Select("select cid from student_course where sid=#{sid}")
    List<String>selectCourseIdList(String sid);

    @Select("select c.name from student_course sc join course c on sc.cid = c.cid where sc.sid=#{sid}")
    List<String> selectCourseNameList(String sid);

}
