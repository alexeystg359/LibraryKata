package com.alexeistegorescu.librarykata.security.service;

import com.alexeistegorescu.librarykata.exception.LibraryException;
import com.alexeistegorescu.librarykata.security.domain.Role;
import com.alexeistegorescu.librarykata.security.entity.UserEntity;
import com.alexeistegorescu.librarykata.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(final String username, final String password) throws UsernameNotFoundException {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new LibraryException("Username already exists");
        }
        final UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findById(final Long id) {
        return userRepository.findById(id);
    }

}
