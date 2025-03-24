package com.mvo.edublockapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "department")
@Data
@NamedEntityGraph(name = "department with headOfDepartment", attributeNodes = @NamedAttributeNode("headOfDepartment"))
public class Department {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "head_of_department_id")
    private Teacher headOfDepartment;

}
