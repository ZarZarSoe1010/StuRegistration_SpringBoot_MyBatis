package com.studentRegistration.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.studentRegistration.mapper.UserMapper;
import com.studentRegistration.model.User;

@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/userManagement", method = RequestMethod.GET)
    public String UserManagement(ModelMap model) {
        return "redirect:/searchUser?id=&name=";
    }

    @RequestMapping(value = "/setupRegisterUser", method = RequestMethod.GET)
    public ModelAndView setupRegisterUser() {
        return new ModelAndView("USR001", "user", new User());
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") @Validated User user, BindingResult bs,
            ModelMap model) {
        if (bs.hasErrors()) {
            return "USR001";
        } else if (!user.getPassword().equals(user.getCpwd())) {
            model.addAttribute("msg", "Passwords do not match !!");
            model.addAttribute("user", user);
            return "USR001";
        } else {
            List<User> userList = userMapper.selectAllUserByEmail(user.getEmail());
            if (userList.size() > 0) {

                model.addAttribute("msg", "Email already registered !!");
                model.addAttribute("user", user);
                return "USR001";

            }
        }

        String userId = GernerateNewUserId();
        user.setUid(userId);
        userMapper.insertUser(user);

        model.addAttribute("msg", "Register Successful !!");
        model.addAttribute("user", new User());
        return "USR001";
    }

    @RequestMapping(value = "/setupUpdateUser", method = RequestMethod.GET)
    public ModelAndView setupUpdateUser(@RequestParam("selectedUserId") String id) {
        User user = userMapper.selectOneUser(id);
        return new ModelAndView("USR002", "user", user);
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") @Validated User user, BindingResult bs,
            ModelMap model) {
        if (bs.hasErrors()) {
            return "USR002";
        } else if (!user.getPassword().equals(user.getCpwd())) {
            model.addAttribute("msg", "Passwords do not match !!");
            return "USR002";
        } else {
            List<User> userList = userMapper.selectAllUserByEmail(user.getEmail());
            if (userList.size() > 0) {

                model.addAttribute("msg", "Email already registered !!");
                model.addAttribute("user", user);
                return "USR002";

            }
        }
        userMapper.updateUser(user);
        model.addAttribute("msg", "Update Successful!!");
        return "USR002";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("selectedUserId") String id, ModelMap model) {
        userMapper.deleteUser(id);

        model.addAttribute("msg", "Delete Successful");

        return "redirect:/searchUser?id=&name=";
    }

    @RequestMapping(value = "/searchUser", method = RequestMethod.GET)
    public String searchUser(@RequestParam("id") String uid, @RequestParam("name") String uname, ModelMap model) {
        List<User> userBeanList = new ArrayList<User>();
        if (uid.isBlank() && uname.isBlank()) {
            userBeanList = userMapper.selectAllUser();
        } else {
            userBeanList = userMapper.selectByFilter(uid, uname);
        }
        if (userBeanList.size() == 0) {
            model.addAttribute("msg", "User not found!!");
        }

        model.addAttribute("userList", userBeanList);
        return "USR003";
    }

    private String GernerateNewUserId() {
        List<User> userList = userMapper.selectAllUser();
        if (userList.size() == 0) {
            return "USR001";
        } else {
            int tempId = Integer.parseInt(userList.get(userList.size() - 1).getUid().substring(3)) + 1;
            String userId = String.format("USR%03d", tempId);
            return userId;
        }
    }

}