package com.mvo.edublockapi.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
