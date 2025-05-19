package com.mvo.edublockapi.util;

import com.mvo.edublockapi.dto.requestdto.CourseTransientDTO;
import com.mvo.edublockapi.dto.requestdto.DepartmentTransientDTO;
import com.mvo.edublockapi.dto.requestdto.StudentTransientDTO;
import com.mvo.edublockapi.dto.requestdto.TeacherTransientDTO;
import com.mvo.edublockapi.entity.Course;
import com.mvo.edublockapi.entity.Department;
import com.mvo.edublockapi.entity.Student;
import com.mvo.edublockapi.entity.Teacher;

public class DataUtil {
    public static Course getCourseEntity() {
        Course course = new Course();
        course.setTitle("New");
        return course;
    }

    public static CourseTransientDTO getCourseTransientDTO() {
        return new CourseTransientDTO("test");
    }

    public static Department getDepartmentEntity() {
        Department department = new Department();
        department.setName("test");
        return department;
    }

    public static DepartmentTransientDTO getDepartmentTransientDTO() {
        return new DepartmentTransientDTO("test");
    }

    public static Teacher getTeacherEntity() {
        Teacher teacher = new Teacher();
        teacher.setName("test");
        return teacher;
    }

    public static StudentTransientDTO getStudentTransientDTO() {
        return new StudentTransientDTO("test", "test@test.ru");
    }

    public static Student getStudentEntity() {
        Student student = new Student();
        student.setName("test");
        student.setEmail("test@test.ru");
        return student;
    }

    public static TeacherTransientDTO getTeacherTransientDTO() {
        return new TeacherTransientDTO("test");
    }
}
