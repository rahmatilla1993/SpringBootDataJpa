package com.example.springbootdatajpa.repository;

import com.example.springbootdatajpa.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(name = "posts.getAllByUserId.Native")
//    @Query(nativeQuery = true, value = "select p.* from post p where p.user_id = ?1")
    List<Post> getAllByUserId(Integer userId);
}
