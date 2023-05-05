package com.sebastian.bestTravel.api.controllers.error_handler;

import com.sebastian.bestTravel.api.models.response.BaseErrorResponse;
import com.sebastian.bestTravel.api.models.response.ErrorResponse;
import com.sebastian.bestTravel.api.models.response.ErrorsResponse;
import com.sebastian.bestTravel.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

// 400*
@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {
    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundException exception) {
        return ErrorResponse.builder()
                .error(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleIdNotFound(MethodArgumentNotValidException exception) {
        var errors = new ArrayList<String>();
        exception.getAllErrors()
                .forEach(error -> errors.add(error.getDefaultMessage()));
        return ErrorsResponse.builder()
                .errors(errors)
                .status(HttpStatus.BAD_REQUEST.name())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}