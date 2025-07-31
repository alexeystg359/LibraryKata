package com.alexeistegorescu.librarykata.security.service;

import com.alexeistegorescu.librarykata.security.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    void register(String username, String password) ;

    Optional<UserEntity> findById(Long id);

}
