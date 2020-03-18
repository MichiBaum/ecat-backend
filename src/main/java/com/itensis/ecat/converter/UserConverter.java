package com.itensis.ecat.converter;

import com.itensis.ecat.domain.User;
import com.itensis.ecat.dtos.ReturnUserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

	public ReturnUserDto toDto(User user){
		return new ReturnUserDto(
				user.getId(),
				user.getName(),
				user.getPermissions().stream().map(permission -> permission.getName().name()).collect(Collectors.toList()),
				user.getCreationDate()
		);
	}

}
