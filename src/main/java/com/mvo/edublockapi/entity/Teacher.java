package com.mvo.edublockapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = "headOfDepartment", fetch = FetchType.LAZY)
    private Department department;

}
