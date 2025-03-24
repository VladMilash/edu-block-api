CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE teacher
(
    id   uuid default uuid_generate_v4() primary key,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE department
(
    id                    uuid default uuid_generate_v4() primary key,
    name                  VARCHAR(255) NOT NULL,
    head_of_department_id uuid UNIQUE,
    CONSTRAINT fk_head_of_department_id FOREIGN KEY (head_of_department_id) REFERENCES teacher (id) ON DELETE SET NULL
);

CREATE TABLE student
(
    id    uuid default uuid_generate_v4() primary key,
    name  VARCHAR(255)        NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE course
(
    id         uuid default uuid_generate_v4() primary key,
    title      VARCHAR(255) NOT NULL,
    teacher_id uuid,
    CONSTRAINT fk_course_teacher FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON DELETE SET NULL
);

CREATE TABLE student_course
(
    id         uuid default uuid_generate_v4() primary key,
    course_id  uuid,
    student_id uuid,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);
