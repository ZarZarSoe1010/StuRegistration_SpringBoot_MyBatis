package com.studentRegistration.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.studentRegistration.mapper.UserMapper;
import com.studentRegistration.model.User;

@Controller
public class LoginController {
    @Autowired
    UserMapper userMapper;
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() { 
		return new ModelAndView("LGN001","user",new User());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("stuBean") @Validated User user, BindingResult bs,
			HttpSession session,ModelMap model) {		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String currentDate = formatter.format(date);		
		List<User> userResList = userMapper.selectAllUser();
		for (User userInfo : userResList) {
			if (userInfo.getUid().equals(user.getUid()) && userInfo.getPassword().equals(user.getPassword())) {
				session.setAttribute("userInfo", userInfo);
				session.setAttribute("date", currentDate);			
				return new ModelAndView("MNU001");			
			} 		
	}
		model.addAttribute("msg", "ID and password do not match");
		return new ModelAndView("LGN001","user",user);			
}
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ModelAndView logout(ModelMap model, HttpSession session) {
		session.removeAttribute("userInfo");
		session.invalidate();
		return new ModelAndView("LGN001","user",new User());
	}	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String showWelcome() {
		return "MNU001";
	}
	
    
}
