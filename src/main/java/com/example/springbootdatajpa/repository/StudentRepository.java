package com.example.springbootdatajpa.repository;

import com.example.springbootdatajpa.entity.Group;
import com.example.springbootdatajpa.entity.Student;
import com.example.springbootdatajpa.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where extract(year from s.birthDate) between ?1 and ?2")
    List<Student> getAllByBirthDateBetween(Integer from, Integer to);

    @Query(name = "Students.getAllByGender")
    List<Student> getAllByGender(Gender gender);

    @Query(name = "Students.getAllByGenderWithNativeQuery")
    List<Student> getAllByGenderWithNativeQuery(String gender);

    @Query("from Student s where s.group = :group")
    List<Student> getAllByGroup(Group group);
}
