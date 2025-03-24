package com.mvo.edublockapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    @OneToOne(mappedBy = "headOfDepartment")
    private Department department;
}
