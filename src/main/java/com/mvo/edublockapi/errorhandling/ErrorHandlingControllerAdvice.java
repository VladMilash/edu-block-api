package com.mvo.edublockapi.errorhandling;

import com.mvo.edublockapi.exception.ErrorResponse;
import com.mvo.edublockapi.exception.NotFoundEntityException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorResponse> onNotFoundEntityException(NotFoundEntityException e, HttpServletRequest request) {
        String path = request.getRequestURI();
        log.info("Exception from path: {} caught", path);
        ErrorResponse errorResponse = new ErrorResponse(
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date()),
            404,
            "Not Found",
            e.getMessage(),
            path
        );
        log.info("Sending errorResponse");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
    }
}


