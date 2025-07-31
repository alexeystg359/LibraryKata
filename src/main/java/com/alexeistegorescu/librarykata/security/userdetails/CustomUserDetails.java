package com.alexeistegorescu.librarykata.security.userdetails;

import com.alexeistegorescu.librarykata.security.domain.Role;
import com.alexeistegorescu.librarykata.security.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    private final Long Id;

    private final String username;

    private final String password;

    private final Role role;

    public CustomUserDetails(UserEntity user) {
        this.Id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
