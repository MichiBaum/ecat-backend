package com.itensis.ecat.services;

import com.itensis.ecat.domain.User;
import com.itensis.ecat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public List<User> getAll() {
		return userRepository.findAll();
	}

}
