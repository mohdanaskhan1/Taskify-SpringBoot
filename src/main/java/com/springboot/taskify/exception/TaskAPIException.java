package com.springboot.taskify.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class TaskAPIException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public TaskAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
