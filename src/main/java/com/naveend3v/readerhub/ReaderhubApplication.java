package com.naveend3v.readerhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class ReaderhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaderhubApplication.class, args);
	}

	@Configuration
	@EnableWebSecurity
	public class SecurityConfiguration {
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			return http.authorizeHttpRequests((auth) -> auth.anyRequest().permitAll()).build();
		}

	}
}
