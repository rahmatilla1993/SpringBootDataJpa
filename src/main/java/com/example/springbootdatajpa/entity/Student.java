package com.example.springbootdatajpa.entity;

import com.example.springbootdatajpa.utils.DateDeserializer;
import com.example.springbootdatajpa.enums.Gender;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "student")
@EntityListeners({AuditingEntityListener.class})
@NamedQueries({
        @NamedQuery(
                name = "Students.getAllByGender",
                query = "from Student where gender = :gender"
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Students.getAllByGenderWithNativeQuery",
                query = "select *from student where gender = :gender",
                resultClass = Student.class
        )
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name")
    private String fullName;

    @Transient
    private Integer age;

    @Temporal(TemporalType.DATE)
    @JsonDeserialize(using = DateDeserializer.class)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    private Group group;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
