package com.example.springbootdatajpa.repository;

import com.example.springbootdatajpa.entity.AuthUser;
import com.example.springbootdatajpa.entity.IAuthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@RepositoryRestResource(path = "users", excerptProjection = IAuthUser.class)
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByUsername(String username);

    Collection<AuthUser> getAllByUsernameStartingWith(String start);

    Page<AuthUser> findAll(Pageable pageable);
}
