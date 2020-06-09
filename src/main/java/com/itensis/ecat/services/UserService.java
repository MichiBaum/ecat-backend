package com.itensis.ecat.services;

import com.itensis.ecat.domain.Permission;
import com.itensis.ecat.domain.PermissionName;
import com.itensis.ecat.domain.User;
import com.itensis.ecat.dtos.SaveUserDto;
import com.itensis.ecat.repository.PermissionRepository;
import com.itensis.ecat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PermissionRepository permissionRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

	public Optional<User> get(Long id) {
		return userRepository.findById(id);
	}

	public Optional<User> get(String username) { return userRepository.findByName(username); }

	public User save(User user) {
		return userRepository.saveAndFlush(user);
	}

	public User update(User user, SaveUserDto saveUserDto) {
		user.setName(saveUserDto.getName());
		if(saveUserDto.getPassword() != null && !saveUserDto.getPassword().isBlank()){
			user.setPassword(bCryptPasswordEncoder.encode(saveUserDto.getPassword()));
		}
		if(saveUserDto.getPermissions().size() > 0){
			List<PermissionName> permissionNames = saveUserDto.getPermissions().stream()
					.map(PermissionName::valueOf)
					.collect(Collectors.toList());
			List<Permission> permissions = permissionNames.stream()
					.map(permissionRepository::findByName)
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toList());
			user.setPermissions(permissions);
		}
		return user;
	}
}
