package com.mvo.edublockapi.errorhandling;

import com.mvo.edublockapi.exception.ErrorResponse;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorResponse> onNotFoundEntityException(NotFoundEntityException e) {
        ErrorResponse errorResponse = new ErrorResponse(
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date()),
            404,
            "Not Found",
            e.getMessage(),
            e.getPath()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
    }
}


