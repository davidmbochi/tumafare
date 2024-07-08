package com.tumafare.com.tumafare.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((req) -> {
                req
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/home/**").permitAll()
                    .requestMatchers("/dashboard/**").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers("/css/**").permitAll()
                    .requestMatchers("/images/**").permitAll()
                    .requestMatchers("/password/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated();
            }).formLogin((formLogin) -> {
                formLogin
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .defaultSuccessUrl("/mpesa/initiate-mpesa");
            }).logout((logout) -> {
                logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                        .permitAll();
            })
            .authenticationProvider(authenticationProvider);
        return http.build();
    }
}
