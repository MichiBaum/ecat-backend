package com.itensis.ecat.controller;

import com.itensis.ecat.converter.UserConverter;
import com.itensis.ecat.domain.*;
import com.itensis.ecat.dtos.ReturnUserDto;
import com.itensis.ecat.dtos.SaveUserDto;
import com.itensis.ecat.services.UserService;
import com.itensis.ecat.validator.UserValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Api(value = "User Endpoints")
public class UserRestController {

	private final UserService userService;
	private final UserConverter userConverter;
	private final UserValidator userValidator;

	@InitBinder("saveUserDto")
	public void initSaveUserBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE_ADMINS')")
	@ApiOperation(value = "GET all Users")
	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public List<ReturnUserDto> getUsers(){
		return userService.getAll().stream()
				.map(userConverter::toDto)
				.collect(Collectors.toList());
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "GET currently logged in user")
	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public ResponseEntity getUser(Principal principal){
		Optional<User> currentlyLoggedUser = userService.get(principal.getName());
		if(currentlyLoggedUser.isPresent()){
			return new ResponseEntity(userConverter.toDto(currentlyLoggedUser.get()), HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE_ADMINS')")
	@ApiOperation(value = "DELETE the user with the specific ID")
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteUser(@PathVariable(value = "id") User user, Principal principal){
		Optional<User> currentlyLoggedUser = userService.get(principal.getName());
		if(currentlyLoggedUser.isPresent()){
			if(user.getId().equals(currentlyLoggedUser.get().getId())){
				return new ResponseEntity(HttpStatus.FORBIDDEN);
			}
			userService.delete(user);
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

	@CrossOrigin
	@PreAuthorize("hasAuthority('ADMINISTRATE')")
	@ApiOperation(value = "Update or create a User")
	@RequestMapping(value = "/api/users/save", method = RequestMethod.POST)
	public ResponseEntity saveUser(@RequestBody @Valid SaveUserDto saveUserDto, Principal principal){
		User currentlyLoggedUser = userService.get(principal.getName()).orElseThrow();
		UserValidationResult userValidationResult = userService.validate(saveUserDto, currentlyLoggedUser);
		ResponseEntity httpResponse = new ResponseEntity(HttpStatus.FORBIDDEN);

		switch (userValidationResult)
		{
			case OWN_USER_PERMISSION_NOT_CHANGED: {
				User updatedUser = userService.update(currentlyLoggedUser, saveUserDto);
				httpResponse = httpOKwithSavedAndConvertedDto(updatedUser);
				break;
			}
			case ADMIN_NEW_USER: {
				User newUser = userConverter.toEntity(saveUserDto);
				httpResponse = httpOKwithSavedAndConvertedDto(newUser);
				break;
			}
			case CAN_ADMINISTRATE_USERS: {
				Optional<User> oldUser = userService.get(saveUserDto.getId());
				if(oldUser.isEmpty()) { return new ResponseEntity(HttpStatus.BAD_REQUEST); }
				User updatedUser = userService.update(oldUser.get(), saveUserDto);
				httpResponse = httpOKwithSavedAndConvertedDto(updatedUser);
				break;
			}
			case NONE: httpResponse = new ResponseEntity(HttpStatus.FORBIDDEN); break;
		}
		return httpResponse;
	}

	private ResponseEntity httpOKwithSavedAndConvertedDto(User updatedUser) {
		return httpResponseOKWith(userConverter.toDto(userService.save(updatedUser)));
	}



	private ResponseEntity httpResponseOKWith(ReturnUserDto user){
		return new ResponseEntity(user, HttpStatus.OK);
	}

}
