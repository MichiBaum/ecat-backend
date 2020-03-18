package com.itensis.ecat.repository;

import com.itensis.ecat.domain.User;

import java.util.Optional;

public interface UserRepository extends CustomJpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
