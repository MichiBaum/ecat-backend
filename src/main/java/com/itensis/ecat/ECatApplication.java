package com.itensis.ecat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

@SpringBootApplication
@PropertySources({
		@PropertySource("classpath:application.properties"),
})
public class ECatApplication extends SpringBootServletInitializer {

	@Resource
	private Environment environment;

	public static void main(String[] args){
		SpringApplication.run(ECatApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@PostConstruct
	void checkImageFolderExists() throws Exception {
		String imagePath = environment.getRequiredProperty("product.image.path");
		File folder = new File(imagePath);
		if(!folder.exists()){
			throw new Exception("Folder with path: " + imagePath + " does not exist");
		}
	}

}
