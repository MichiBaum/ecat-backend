package com.itensis.ecat.controller;

import com.itensis.ecat.converter.UserConverter;
import com.itensis.ecat.domain.Product;
import com.itensis.ecat.domain.User;
import com.itensis.ecat.dtos.ReturnUserDto;
import com.itensis.ecat.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(value = "User Endpoints")
class UserRestController {

	private final UserService userService;
	private final UserConverter userConverter;

	@ApiOperation(value = "GET all Users")
	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public List<ReturnUserDto> getUsers(){
		return userService.getAll().stream()
				.map(userConverter::toDto)
				.collect(Collectors.toList());
	}

	@PreAuthorize("hasAuthority('ADMINISTRATE_ADMINS')")
	@ApiOperation(value = "DELETE the user with the specific ID")
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable(value = "id") User user){
		userService.delete(user);
	}

}
