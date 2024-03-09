package com.example.springbootdatajpa.controller;

import com.example.springbootdatajpa.dto.StudentDto;
import com.example.springbootdatajpa.entity.Group;
import com.example.springbootdatajpa.entity.Student;
import com.example.springbootdatajpa.enums.Gender;
import com.example.springbootdatajpa.exception.NotFoundException;
import com.example.springbootdatajpa.logging.Logging;
import com.example.springbootdatajpa.repository.GroupRepository;
import com.example.springbootdatajpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final Supplier<NotFoundException> supplier = () -> new NotFoundException("Student not found!");

    private static void run() {
        throw new NotFoundException("Student not found");
    }

    @Autowired
    public StudentController(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @GetMapping("/group/{groupId}")
    public List<Student> getAllByGroup(@PathVariable("groupId") long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group not found"));
        return studentRepository.getAllByGroup(group);
    }

    @GetMapping("/bypage")
    public Page<Student> getAllByPageable(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return studentRepository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/bygender")
    public List<Student> getAllByGender(@RequestParam("gender") String gender) {
        return studentRepository.getAllByGenderWithNativeQuery(gender);
    }

    @GetMapping("/byyear")
    public List<Student> getAllByYear(@RequestParam(name = "from") Integer from,
                                      @RequestParam(name = "to") Integer to) {
        return studentRepository.getAllByBirthDateBetween(from, to);
    }

    @GetMapping("/{id}")
    public HttpEntity<Student> getOne(@PathVariable("id") long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(supplier);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    @Logging(message = "save new student")
    public HttpEntity<Void> save(@RequestBody StudentDto dto) throws ParseException {
        studentRepository.save(Student.builder()
                .fullName(dto.getFullName())
                .birthDate(dateFormat.parse(dto.getBirthDate()))
                .gender(Gender.valueOf(dto.getGender()))
                .age(dto.getAge())
                .build());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Logging(message = "edit student")
    public HttpEntity<Void> edit(@PathVariable("id") long id,
                                 @RequestBody StudentDto dto) throws ParseException {
        Student student = studentRepository.findById(id)
                .orElseThrow(supplier);
        student.setAge(dto.getAge());
        student.setGender(Gender.valueOf(dto.getGender()));
        student.setFullName(dto.getFullName());
        student.setBirthDate(dateFormat.parse(dto.getBirthDate()));
        studentRepository.save(student);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Logging(message = "delete student")
    public HttpEntity<Void> delete(@PathVariable("id") long id) {
        studentRepository.findById(id)
                .ifPresentOrElse(
                        (studentRepository::delete),
                        StudentController::run
                );
        return ResponseEntity.noContent().build();
    }
}
