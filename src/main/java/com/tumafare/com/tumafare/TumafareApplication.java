package com.tumafare.com.tumafare;

import com.tumafare.com.tumafare.role.Role;
import com.tumafare.com.tumafare.role.RoleRepository;
import com.tumafare.com.tumafare.user.User;
import com.tumafare.com.tumafare.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
public class TumafareApplication {

	public static void main(String[] args) {
		SpringApplication.run(TumafareApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository,
											   UserRepository userRepository,
											   PasswordEncoder passwordEncoder) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()){
				roleRepository.save(
						Role.builder().name("USER").build()
				);
			}if (roleRepository.findByName("ADMIN").isEmpty()){
				roleRepository.save(
						Role.builder().name("ADMIN").build()
				);
			}

			User admin = User.builder()
					.firstname("david")
					.lastname("mbochi")
					.email("davidmbochinjonge@gmail.com")
					.password(passwordEncoder.encode("@HUNTERmbo83"))
					.accountLocked(false)
					.enabled(true)
					.roles(List.of(roleRepository.findByName("USER").get(),
							roleRepository.findByName("ADMIN").get()))
					.build();

			userRepository.save(admin);
		};
	}

}
