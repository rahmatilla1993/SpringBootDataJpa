package com.example.springbootdatajpa;

import com.example.springbootdatajpa.entity.Group;
import com.example.springbootdatajpa.entity.Student;
import com.example.springbootdatajpa.repository.GroupRepository;
import com.example.springbootdatajpa.repository.StudentRepository;
import com.example.springbootdatajpa.utils.SessionUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.URL;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootDataJpaApplication {
    private final URL groupUrl = SpringBootDataJpaApplication.class.getClassLoader().getResource("group.json");
    private final URL studentUrl = SpringBootDataJpaApplication.class.getClassLoader().getResource("students.json");
    private final SessionUser sessionUser;

    @Autowired
    public SpringBootDataJpaApplication(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJpaApplication.class, args);
    }

    //        @Bean
    ApplicationRunner runner(ObjectMapper objectMapper,
                             GroupRepository groupRepository,
                             StudentRepository studentRepository) {
        return (args -> {
            List<Group> groups = objectMapper.readValue(groupUrl, new TypeReference<>() {
            });
            List<Student> students = objectMapper.readValue(studentUrl, new TypeReference<>() {
            });
            groupRepository.saveAll(groups);
            studentRepository.saveAll(students);
        });
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return sessionUser::getUsername;
    }
}
