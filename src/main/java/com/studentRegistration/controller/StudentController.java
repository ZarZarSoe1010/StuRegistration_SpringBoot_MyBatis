package com.studentRegistration.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.studentRegistration.mapper.CourseMapper;
import com.studentRegistration.mapper.StudentMapper;
import com.studentRegistration.model.Course;
import com.studentRegistration.model.Student;

@Controller
public class StudentController {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    CourseMapper courseMapper;

    @RequestMapping(value = "/setupSearchStudent", method = RequestMethod.GET)
    public String setupSearchStudent(ModelMap model) {
        return "redirect:/searchStudent?sid=&sname=&scourse=";
    }

    @RequestMapping(value = "/setupRegisterStudent", method = RequestMethod.GET)
    public ModelAndView setupRegisterStudent(ModelMap model) {
        List<Course> courseList = courseMapper.selectAllCourse();

        String studentId = GenerateNewStudentId();
        Student student = new Student();
        student.setSid(studentId);

        model.addAttribute("courseList", courseList);
        return new ModelAndView("STU001", "student", student);
    }

    @RequestMapping(value = "/registerStudent", method = RequestMethod.POST)
    public ModelAndView registerStudent(@ModelAttribute("student") @Validated Student student, BindingResult bs,
            ModelMap model) {
        if (bs.hasErrors()) {
            List<Course> courseList = courseMapper.selectAllCourse();
            model.addAttribute("courseList", courseList);
            return new ModelAndView("STU001", "student", student);
        }
        studentMapper.insertStudent(student);
        insertStudent_Course(student.getSid(), student.getCourses());
        model.addAttribute("msg", "Register Successful");

        // rebuild new Student Object
        String studentId = GenerateNewStudentId();
        student = new Student();
        student.setSid(studentId);

        List<Course> courseList = courseMapper.selectAllCourse();
        model.addAttribute("courseList", courseList);
        return new ModelAndView("STU001", "student", student);
    }

    @RequestMapping(value = "/seeMore", method = RequestMethod.GET)
    public ModelAndView seeMore(@RequestParam("selectedStudentId") String id, ModelMap model) {
        Student student = studentMapper.selectOneStudent(id);
        List<Course> courseList = courseMapper.selectAllCourse();
        List<String> stuCourseIdList = studentMapper.selectCourseIdList(id);
        student.setCourses(stuCourseIdList);
        model.addAttribute("courseList", courseList);
        return new ModelAndView("STU002", "student", student);
    }

    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public ModelAndView updateStudent(@ModelAttribute("student") @Validated Student student, BindingResult bs,
            ModelMap model) {
        List<Course> courseList = courseMapper.selectAllCourse();
        model.addAttribute("courseList", courseList);
        if (bs.hasErrors()) {
            return new ModelAndView("STU002", "student", student);
        }
        studentMapper.updateStudent(student);
        studentMapper.deleteStudent_Course(student.getSid());
        insertStudent_Course(student.getSid(), student.getCourses());

        model.addAttribute("msg", " Update Successful!!!");
        return new ModelAndView("STU002", "student", student);
    }

    @RequestMapping(value = "/deleteStudent", method = RequestMethod.GET)
    public String deleteStudent(@RequestParam("selectedStudentId") String id, ModelMap model) {
        studentMapper.deleteStudent(id);
        return "redirect:/searchStudent?sid=&sname=&scourse=";
    }

    @RequestMapping(value = "/searchStudent", method = RequestMethod.GET)
    public ModelAndView searchStudent(@RequestParam("sid") String sid, @RequestParam("sname") String sname,
            @RequestParam("scourse") String scourse, ModelMap model) {

        String stuId = sid.isBlank() ? sid : ("%" + sid + "%");
        String stuName = sname.isBlank() ? sname : ("%" + sname + "%");
        String stuCourse = scourse.isBlank() ? scourse : ("%" + scourse + "%"); 

        List<Student> stuBeanList = new ArrayList<Student>();
        if (sid.isBlank() && sname.isBlank() && scourse.isBlank()) {
            stuBeanList = studentMapper.selectAllStudent();

        } else {
            stuBeanList = studentMapper.selectStudentListByIdOrNameOrCourse(stuId, stuName, stuCourse);
        }
        if (stuBeanList.size() == 0) {
            model.addAttribute("msg", "Student not found!!");
        }
        List<Student>distinctStudentList=stuBeanList.stream().distinct().collect(Collectors.toList());
        
        for (Student stu : distinctStudentList) {
            List<String> courseNameList = studentMapper.selectCourseNameList(stu.getSid());
            stu.setCourses(courseNameList);
        }
        
        return new ModelAndView("STU003", "stuList", distinctStudentList);
    }

    private void insertStudent_Course(String stuId, List<String> courseIdList) {
        for (String courseId : courseIdList) {
            studentMapper.insertStudent_Course(stuId, courseId);
        }
    }

    private String GenerateNewStudentId() {
        List<Student> stuList = studentMapper.selectAllStudent();
        if (stuList.size() == 0) {
            return "STU001";
        } else {
            int tempId = Integer.parseInt(stuList.get(stuList.size() - 1).getSid().substring(3)) + 1;
            String stuId = String.format("STU%03d", tempId);
            return stuId;
        }
    }
}
