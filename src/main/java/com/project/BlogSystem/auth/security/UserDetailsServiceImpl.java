package com.project.BlogSystem.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.BlogSystem.auth.model.entity.AuthUser;
import com.project.BlogSystem.auth.repository.AuthUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));

        return new UserDetailsImpl(user);
    }

}
