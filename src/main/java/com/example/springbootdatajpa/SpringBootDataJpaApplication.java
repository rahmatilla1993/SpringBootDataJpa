package com.example.springbootdatajpa;

import com.example.springbootdatajpa.entity.Post;
import com.example.springbootdatajpa.repository.PostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URL;
import java.util.List;

@SpringBootApplication
public class SpringBootDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJpaApplication.class, args);
    }

//    @Bean
    ApplicationRunner runner(PostRepository postRepository,
                             ObjectMapper objectMapper) {
        return (args -> {
            List<Post> posts = objectMapper.readValue(
                    new URL("https://jsonplaceholder.typicode.com/posts"),
                    new TypeReference<>() {
                    });
            postRepository.saveAll(posts);
        });
    }
}
