package com.mvo.edublockapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "student")
@Data
@NamedEntityGraph(name = "student with courses", attributeNodes = @NamedAttributeNode("courses"))
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;
}
