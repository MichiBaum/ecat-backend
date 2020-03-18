package com.itensis.ecat.security;

class SecurityConstants {
	final static String SECRET = "SecretKeyToGenJWTs";
	final static Long EXPIRATION_TIME = 10L * 24L * 3600L * 1000L;  // 10 days
	final static String TOKEN_PREFIX = "Bearer ";
	final static String HEADER_STRING = "Authorization";
}
