package com.mvo.edublockapi.exception;


public class NotFoundEntityException extends ApiException {

    public NotFoundEntityException(String message, String path) {
        super(message, path);
    }
}
