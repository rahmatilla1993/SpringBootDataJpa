package com.example.springbootdatajpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

import static org.hibernate.annotations.CascadeType.PERSIST;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "groups")
@Data
@JsonIgnoreProperties({"students"})
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "group_name")
    private String name;
    @OneToMany(mappedBy = "group")
    @Cascade({PERSIST})
    private List<Student> students;
}
