package com.itensis.ecat.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

class LoginDto {
	private String name;
	private String password;
}
