package com.example.springbootdatajpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedQueries({
        @NamedQuery(
                name = "posts.getAllByUserId",
                query = "select p from Post p where p.userId = ?1"
        )
})
@NamedNativeQuery(
        name = "posts.getAllByUserId.Native",
        query = "select * from post where user_id = ?1",
        resultClass = Post.class
)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String body;
    @Column(name = "user_id")
    private int userId;
}
