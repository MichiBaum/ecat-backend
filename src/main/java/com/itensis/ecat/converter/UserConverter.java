package com.itensis.ecat.converter;

import com.itensis.ecat.domain.Permission;
import com.itensis.ecat.domain.PermissionName;
import com.itensis.ecat.domain.User;
import com.itensis.ecat.dtos.ReturnUserDto;
import com.itensis.ecat.dtos.SaveUserDto;
import com.itensis.ecat.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {

	private final PermissionRepository permissionRepository;

	public ReturnUserDto toDto(User user){
		return new ReturnUserDto(
				user.getId(),
				user.getName(),
				user.getPermissions().stream().map(permission -> permission.getName().name()).collect(Collectors.toList()),
				user.getCreationDate()
		);
	}

	public User toEntity(SaveUserDto saveUserDto) {
		List<PermissionName> permissionNames = saveUserDto.getPermissions().stream()
				.map(PermissionName::valueOf)
				.collect(Collectors.toList());
		List<Permission> permissions = permissionNames.stream()
				.map(permissionRepository::findByName)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
		return new User(saveUserDto.getName(), saveUserDto.getPassword(), permissions, new Date().getTime());
	}
}
