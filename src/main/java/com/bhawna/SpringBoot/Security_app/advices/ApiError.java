package com.bhawna.SpringBoot.Security_app.advices;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus status;

    public ApiError(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public ApiError() {
        this.timeStamp = LocalDateTime.now();
    }
}
