package com.mvo.edublockapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "student")
@Data
@NamedEntityGraph(
    name = "Student.fullGraph",
    attributeNodes = {
        @NamedAttributeNode(value = "courses", subgraph = "courseDetails")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "courseDetails",
            attributeNodes = {
                @NamedAttributeNode(value = "teacher", subgraph = "teacherDetails")
            }
        ),
        @NamedSubgraph(
            name = "teacherDetails",
            attributeNodes = {
                @NamedAttributeNode("courses"),
                @NamedAttributeNode("department")
            }
        )
    }
)
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

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

}
