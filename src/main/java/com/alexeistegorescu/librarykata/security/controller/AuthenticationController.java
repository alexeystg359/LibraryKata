package com.alexeistegorescu.librarykata.security.controller;

import com.alexeistegorescu.librarykata.security.service.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/registration")
    public String register(@RequestParam @NotBlank(message = "username is mandatory") final String username,
                           @RequestParam @NotBlank(message = "password is mandatory") final String password) {
        userService.register(username, password);
        return "Registration is successful for user " + username;
    }
}
