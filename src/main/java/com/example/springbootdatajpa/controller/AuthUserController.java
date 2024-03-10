package com.example.springbootdatajpa.controller;

import com.example.springbootdatajpa.entity.AuthUser;
import com.example.springbootdatajpa.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hateoas/users")
public class AuthUserController {

    private final AuthUserRepository authUserRepository;
    private final AuthUserModelAssembler authUserModelAssembler;

    @Autowired
    public AuthUserController(AuthUserRepository authUserRepository,
                              AuthUserModelAssembler authUserModelAssembler) {
        this.authUserRepository = authUserRepository;
        this.authUserModelAssembler = authUserModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<AuthUser>> findAll() {
        List<AuthUser> userList = authUserRepository.findAll();
        return authUserModelAssembler.toCollectionModel(userList);
    }

    @GetMapping("/{id}")
    public EntityModel<AuthUser> getOne(@PathVariable("id") int id) {
        AuthUser authUser = authUserRepository.findById((long) id).get();
        System.out.println(authUser);
        return authUserModelAssembler.toModel(authUser);
    }

    @PostMapping("/add")
    public HttpEntity<Void> save(@RequestBody AuthUser authUser) {
        Optional<AuthUser> optionalAuthUser = authUserRepository.findByUsername(authUser.getUsername());
        if (optionalAuthUser.isPresent()) {
            throw new RuntimeException("User with '%s' username already taken");
        }
        authUserRepository.save(authUser);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/edit/{id}")
    public HttpEntity<Void> edit(@PathVariable("id") int id, @RequestBody AuthUser authUser) {
        authUser.setId((long) id);
        authUserRepository.save(authUser);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<Void> delete(@PathVariable("id") int id) {
        authUserRepository.deleteById((long) id);
        return ResponseEntity.noContent()
                .build();
    }
}
