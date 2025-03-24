package com.mvo.edublockapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "student")
@Data
@NamedEntityGraph(name = "student with courses", attributeNodes = @NamedAttributeNode("courses"))
public class Student {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
}
