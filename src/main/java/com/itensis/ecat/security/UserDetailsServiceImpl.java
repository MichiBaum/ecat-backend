package com.itensis.ecat.security;

import com.itensis.ecat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.itensis.ecat.domain.User user = userRepository.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));

		List<GrantedAuthority> authorities = user.getPermissions().stream()
				.map(permission -> new SimpleGrantedAuthority(permission.getName().name()))
				.collect(Collectors.toList());

		return new User(user.getName(), user.getPassword(), authorities);
	}
}
