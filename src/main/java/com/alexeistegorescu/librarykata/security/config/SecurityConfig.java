package com.alexeistegorescu.librarykata.security.config;

import com.alexeistegorescu.librarykata.exception.LibraryException;
import com.alexeistegorescu.librarykata.security.domain.Role;
import com.alexeistegorescu.librarykata.security.entity.UserEntity;
import com.alexeistegorescu.librarykata.security.repository.UserRepository;
import com.alexeistegorescu.librarykata.security.userdetails.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${library.admin.password:secret}")
    private String adminPassword;

    @Bean
    public UserDetailsService userDetailsService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        userRepository.save(UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ADMIN)
                .build());

        return username -> {
            UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new LibraryException("User not found"));
            return new CustomUserDetails(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth/registration").permitAll()
                        .requestMatchers("/v1/book/all").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers("/v1/book/add").hasAnyRole(Role.ADMIN.name())
                        .requestMatchers("/v1/book/borrow").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers("/v1/book/my").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                        .requestMatchers("/v1/book/return").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
