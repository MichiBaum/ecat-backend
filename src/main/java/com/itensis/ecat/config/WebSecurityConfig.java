package com.itensis.ecat.config;

import com.itensis.ecat.annotation.PublicEndpointDetails;
import com.itensis.ecat.repository.UserRepository;
import com.itensis.ecat.security.JWTAuthenticationFilter;
import com.itensis.ecat.security.JWTAuthorizationFilter;
import com.itensis.ecat.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private final List<PublicEndpointDetails> publicEndpoints;

	@Value("${swagger2.enabled}")
	private boolean swagger2Enabled;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<String> antEndpoints = publicEndpoints.stream()
				.map(PublicEndpointDetails::getAntPaths)
				.flatMap(Collection::parallelStream)
				.collect(Collectors.toList());
		antEndpoints.add("/swagger-ui.html");
		antEndpoints.forEach(antEndpoint -> log.info("Public AntEndpoint '" + antEndpoint + "' found"));

		http.cors().and().authorizeRequests()
				.antMatchers(antEndpoints.toArray(new String[antEndpoints.size()])).permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager(), userRepository, bCryptPasswordEncoder))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService))
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.csrf().disable();
		http.headers().frameOptions().disable();
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
