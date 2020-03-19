package com.itensis.ecat.config;

import com.itensis.ecat.repository.UserRepository;
import com.itensis.ecat.security.JWTAuthenticationFilter;
import com.itensis.ecat.security.JWTAuthorizationFilter;
import com.itensis.ecat.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsServiceImpl userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;

	@Value("${swagger2.enabled}")
	private boolean swagger2Enabled;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests() //TODO add open for all endpoints
				//Promotion endpoints
				.antMatchers(HttpMethod.GET,"/api/promotions/{\\d+}").permitAll()
				.antMatchers(HttpMethod.GET,"/api/promotions").permitAll()
				//Product endpoints
				.antMatchers(HttpMethod.GET,"/api/products/{\\d+}").permitAll()
				//User endpoints
				.antMatchers(HttpMethod.GET, "/api/users").permitAll()
				//ProductGroup endpoints
				.antMatchers(HttpMethod.GET, "/api/productsgroups").permitAll()
				//Swagger endpoints
				.antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
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
