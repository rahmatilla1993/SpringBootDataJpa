package com.example.springbootdatajpa.controller;

import com.example.springbootdatajpa.dto.GroupDto;
import com.example.springbootdatajpa.entity.Group;
import com.example.springbootdatajpa.exception.NotFoundException;
import com.example.springbootdatajpa.logging.Logging;
import com.example.springbootdatajpa.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupRepository groupRepository;
    private final Supplier<NotFoundException> supplier = () -> new NotFoundException("Group not found");

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping("/all")
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @GetMapping("/byname")
    public HttpEntity<Group> findByName(@RequestParam(name = "name") String name) {
        Group group = groupRepository
                .findByName(name)
                .orElseThrow(supplier);
        return ResponseEntity.ok(group);
    }

    @PostMapping("/add")
    @Logging(message = "add group")
    public HttpEntity<Void> save(@RequestBody GroupDto dto) {
        groupRepository.save(Group.builder()
                .name(dto.getName())
                .build());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{id}")
    @Logging(message = "edit group")
    public HttpEntity<Void> edit(@PathVariable("id") long id, @RequestBody GroupDto dto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(supplier);
        group.setName(dto.getName());
        groupRepository.save(group);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    @Logging(message = "delete group")
    public HttpEntity<Void> delete(@PathVariable("id") long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(supplier);
        groupRepository.delete(group);
        return ResponseEntity.noContent().build();
    }
}
