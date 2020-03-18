package com.itensis.ecat.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
class TokenDto {
	private String headerName;
	private String token;
	private Date expiresAt;
	private List<String> permissions;
	private String username;
}
