package com.mvo.edublockapi.dto.requestdto;

import jakarta.validation.constraints.NotBlank;

public record TeacherTransientDTO
    (@NotBlank
     String name) {
}
