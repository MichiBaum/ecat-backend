package com.itensis.ecat;

import com.itensis.ecat.annotation.PublicEndpointSearcher;
import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.annotation.PublicEndpointDetails;
import lombok.extern.slf4j.Slf4j;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@PropertySources({
		@PropertySource("classpath:application.properties"),
})
@Slf4j
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
	void checkProductImageFolderExists() throws Exception {
		String imagePath = environment.getRequiredProperty("product.image.path");
		File folder = new File(imagePath);
		if(!folder.exists()){
			throw new Exception("Folder with path: " + imagePath + " does not exist");
		}
	}

	@PostConstruct
	void checkPromotionImageFolderExists() throws Exception {
		String imagePath = environment.getRequiredProperty("promotion.image.path");
		File folder = new File(imagePath);
		if(!folder.exists()){
			throw new Exception("Folder with path: " + imagePath + " does not exist");
		}
	}

	@Bean
	public List<PublicEndpointDetails> publicEndpoints() {
		PublicEndpointSearcher publicEndpointSearcher = new PublicEndpointSearcher("com.itensis.ecat.controller");
		List<Class> restControllers = publicEndpointSearcher.getAllRestController();
		List<Method> methods = restControllers.stream()
				.map(clazz -> publicEndpointSearcher.getMethodsAnnotatedWith(clazz, PublicEndpoint.class))
				.flatMap(Collection::parallelStream)
				.collect(Collectors.toList());
		return methods.stream()
				.map(publicEndpointSearcher::getRequestMappingValue)
				.flatMap(Collection::parallelStream)
				.collect(Collectors.toList());
	}

}
