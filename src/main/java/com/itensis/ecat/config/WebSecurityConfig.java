package com.itensis.ecat.config;

import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.annotation.AnnotationSearcher;
import com.itensis.ecat.repository.UserRepository;
import com.itensis.ecat.security.JWTAuthenticationFilter;
import com.itensis.ecat.security.JWTAuthorizationFilter;
import com.itensis.ecat.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;

	@Value("${swagger2.enabled}")
	private boolean swagger2Enabled;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> endpoints = getAllPublicEndpoints();
		List<String> antEndpoints = convertToAntPatterns(endpoints);
		antEndpoints.add("/swagger-ui.html");

		http.cors().and().authorizeRequests()
				//Promotion endpoints
				.antMatchers(antEndpoints.toArray(new String[antEndpoints.size()])).permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), userRepository, bCryptPasswordEncoder))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	private List<String> convertToAntPatterns(List<String> endpoints) {
		List<String> newEndpoints = new ArrayList<>();
		for(String endpoint : endpoints){
			if(endpoint.contains("{") && endpoint.contains("}")){
				String newEndpoint = endpoint.replaceAll("\\{.+}", "{\\\\d+}");
				newEndpoints.add(newEndpoint);
			}else{
				newEndpoints.add(endpoint);
			}
		}
		return newEndpoints;
	}

	private List<String> getAllPublicEndpoints() {
		AnnotationSearcher annotationSearcher = new AnnotationSearcher();
		List<String> endpoints = new ArrayList<>();
		for(Class clazz : annotationSearcher.getAllRestController()){
			List<Method> methods = annotationSearcher.getMethodsAnnotatedWith(clazz, PublicEndpoint.class);
			endpoints.addAll(annotationSearcher.getRequestMappingValue(methods));
		}
		return endpoints;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	public void configure(WebSecurity web){
		if(swagger2Enabled){
			web.ignoring().antMatchers(
					"/v2/api-docs",
					"/configuration/ui",
					"/swagger-resources/**",
					"/configuration/security",
					"/swagger-ui.html",
					"/webjars/**");
		}
	}

}
