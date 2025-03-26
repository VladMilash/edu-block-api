package com.mvo.edublockapi.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    protected String path;

    public ApiException(String message, String path) {
        super(message);
        this.path = path;
    }
}
