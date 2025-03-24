package com.mvo.edublockapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "course")
@Data
@NamedEntityGraph(name = "course with students", attributeNodes = @NamedAttributeNode("students"))
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students;
}
