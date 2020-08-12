package com.itensis.ecat.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itensis.ecat.exception.ErrorDetails;
import com.itensis.ecat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Value("${errors.exceptionClass.send}")
	private boolean sendExceptionClass;

	private final AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
		this.setFilterProcessesUrl("/api/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		try {
			LoginDto credentials = new ObjectMapper().readValue(req.getInputStream(), LoginDto.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							credentials.getName(),
							credentials.getPassword(),
							new ArrayList<>())
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
		Date expiresAt = new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME);
		String username = ((User) auth.getPrincipal()).getUsername();
		String token = JWT.create()
				.withSubject(username)
				.withExpiresAt(expiresAt)
				.sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

		List<String> permissions = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		TokenDto jwt = new TokenDto(
				SecurityConstants.HEADER_STRING,
				SecurityConstants.TOKEN_PREFIX + token,
				expiresAt,
				permissions,
				username);

		response.setStatus(HttpServletResponse.SC_OK);
		response.addHeader("Content-Type", "application/json");
		response.getWriter().write(new ObjectMapper().writeValueAsString(jwt));
		response.getWriter().flush();
		response.getWriter().close();
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)throws IOException{
		ErrorDetails errorDetails = new ErrorDetails(new Date().getTime(), "errors.login.wrongCredentials", failed.getClass(), request.getPathInfo(), sendExceptionClass);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
		response.getWriter().close();
	}

}
