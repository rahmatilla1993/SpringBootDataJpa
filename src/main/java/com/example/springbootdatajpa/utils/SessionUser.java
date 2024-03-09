package com.example.springbootdatajpa.utils;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SessionUser {

    public Optional<String> getUsername() {
        return Optional.of("user");
    }
}
