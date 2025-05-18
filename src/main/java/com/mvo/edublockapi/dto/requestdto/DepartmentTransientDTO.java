package com.mvo.edublockapi.dto.requestdto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentTransientDTO
    (@NotBlank
     String name) {
}
