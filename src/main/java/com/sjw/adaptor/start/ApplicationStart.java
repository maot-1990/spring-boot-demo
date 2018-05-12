package com.sjw.adaptor.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages={"com.sjw.adaptor"})
//@MapperScan(basePackages={"com.sjw.adaptor.dao"})
//@EnableTransactionManagement
public class ApplicationStart {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
