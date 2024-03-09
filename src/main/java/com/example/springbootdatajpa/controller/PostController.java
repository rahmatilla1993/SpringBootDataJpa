package com.example.springbootdatajpa.controller;

import com.example.springbootdatajpa.entity.Post;
import com.example.springbootdatajpa.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @GetMapping("/sorting")
    public List<Post> sort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id")
                .and(Sort.by(Sort.Direction.ASC, "title"));
        return postRepository.findAll(sort);
    }

    @GetMapping("/paged")
    public Page<Post> paging(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        Sort sort = Sort.by(Sort.Order.asc("title"));
        PageRequest pageable = PageRequest.of(page, size, sort);
        return postRepository.findAll(pageable);
    }

    @GetMapping("/{userId}")
    public List<Post> getAllByUserId(@PathVariable("userId") int userId) {
        return postRepository.getAllByUserId(userId);
    }
}
