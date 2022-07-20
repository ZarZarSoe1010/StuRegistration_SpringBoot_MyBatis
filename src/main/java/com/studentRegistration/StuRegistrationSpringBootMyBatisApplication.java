package com.studentRegistration;

import java.io.FileNotFoundException;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.studentRegistration.mapper.UserReport;
import com.studentRegistration.model.User;

import net.sf.jasperreports.engine.JRException;

@RestController
@MappedTypes(User.class)
@MapperScan("com.studentRegistration.mapper")
@SpringBootApplication

public class StuRegistrationSpringBootMyBatisApplication {
	@Autowired
    UserReport userReport;
	public static void main(String[] args) {
		SpringApplication.run(StuRegistrationSpringBootMyBatisApplication.class, args);
	}
	@RequestMapping(value ="/report/{format}", method = RequestMethod.GET)
    public String generatedReport(@PathVariable String format) throws FileNotFoundException, JRException {
      return   userReport.exportReport(format);
       //return "redirect:/searchUser?id=&name=";
    }

}
