package com.studentRegistration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.studentRegistration.mapper.CourseMapper;
import com.studentRegistration.model.Course;

@Controller
public class CourseController {
    @Autowired
    CourseMapper courseMapper;

    @RequestMapping(value = "/setupRegisterCourse", method = RequestMethod.GET)
	public ModelAndView setupRegisterCourse() {
		Course course= new Course();
		List<Course> courseList =courseMapper.selectAllCourse();
		if (courseList.size() == 0) {
			course.setCid("COU001");
		} else {
			int tempId = Integer.parseInt(courseList.get(courseList.size() - 1).getCid().substring(3)) + 1;
			String courseId = String.format("COU%03d", tempId);
			course.setCid(courseId);
		}
		return new ModelAndView("BUD003","course",course);
	}
	@RequestMapping(value = "/registerCourse", method = RequestMethod.POST)
	public String registerCourse(@ModelAttribute("course") @Validated Course course, BindingResult bs,
			ModelMap model) {
		if (bs.hasErrors()) {
			return "BUD003";
		}

			courseMapper.insertCourse(course);
			model.addAttribute("msg", "Register Succesful !!");
			 course=new Course();
			List<Course> courseList = courseMapper.selectAllCourse();
			if (courseList.size() == 0) {
				course.setCid("COU001");
			} else {
				int tempId = Integer.parseInt(courseList.get(courseList.size() - 1).getCid().substring(3)) + 1;
				String courseId = String.format("COU%03d", tempId);
				course.setCid(courseId);
			}
			model.addAttribute("course",course);
			return "BUD003";
		}
    
}
