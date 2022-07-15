package com.studentRegistration;

import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.studentRegistration.model.User;

@MappedTypes(User.class)
@MapperScan("com.studentRegistration.mapper")
@SpringBootApplication
public class StuRegistrationSpringBootMyBatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(StuRegistrationSpringBootMyBatisApplication.class, args);
	}

}
