package com.example.springbootdatajpa;

import com.example.springbootdatajpa.entity.AuthUser;
import com.example.springbootdatajpa.repository.AuthUserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@SpringBootApplication
public class SpringBootDataJpaApplication {
    private final URL url = SpringBootDataJpaApplication.class.getClassLoader().getResource("users.json");

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJpaApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(ObjectMapper objectMapper, AuthUserRepository authUserRepository) {
        return (args -> {
            try {
                List<AuthUser> list = objectMapper.readValue(url, new TypeReference<>() {
                });
                authUserRepository.saveAll(list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
