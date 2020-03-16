package com.itensis.ecat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
		@PropertySource("classpath:application.properties"),
})
public class ECatApplication extends SpringBootServletInitializer {

	public static void main(String[] args){
		SpringApplication.run(ECatApplication.class, args);
	}


}
