package com.itensis.ecat.controller;

import com.itensis.ecat.annotation.PublicEndpoint;
import com.itensis.ecat.converter.UserConverter;
import com.itensis.ecat.domain.Product;
import com.itensis.ecat.domain.Promotion;
import com.itensis.ecat.domain.User;
import com.itensis.ecat.dtos.ReturnUserDto;
import com.itensis.ecat.dtos.SavePromotionDto;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.itensis.ecat.utilities.OptionalUtilities.ifPresentElseThrow;

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


	@PublicEndpoint
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
	public void deleteProduct(@PathVariable(value = "id") Optional<User> user){
		userService.delete(ifPresentElseThrow(user));
	}

	@PreAuthorize("hasAuthority('ADMINISTRATE_ADMINS')")
	@ApiOperation(value = "Update or create a User")
	@RequestMapping(value = "/api/users/save", method = RequestMethod.POST)
	public ResponseEntity saveUser(@RequestBody @Valid SaveUserDto saveUserDto){
		User user;
		if(saveUserDto.getId() == null || saveUserDto.getId() == 0){
			user = userConverter.toEntity(saveUserDto);
		}else{
			Optional<User> oldUser = userService.get(saveUserDto.getId());
			if(oldUser.isPresent()) {
				user = userService.update(oldUser.get(), saveUserDto);
			}else {
				return new ResponseEntity(HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity(userConverter.toDto(userService.save(user)), HttpStatus.OK);
	}

}
