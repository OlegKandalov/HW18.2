package com.cursor.library;

import com.cursor.library.models.User;
import com.cursor.library.models.UserPermission;
import com.cursor.library.daos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Set;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class LibraryApplication {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@PostConstruct
	public void addUsers() {
		User user1 = new User();
		user1.setUsername("oleg");
		user1.setPassword(passwordEncoder.encode("111"));
		user1.setPermissions(Set.of(UserPermission.ROLE_ADMIN));
		userRepository.save(user1);

		User user2 = new User();
		user2.setUsername("guest");
		user2.setPassword(passwordEncoder.encode("222"));
		user2.setPermissions(Set.of(UserPermission.ROLE_GUEST));
		userRepository.save(user2);
	}
}
