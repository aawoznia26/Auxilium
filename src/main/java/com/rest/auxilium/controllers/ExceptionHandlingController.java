package com.rest.auxilium.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public final String handleException(Exception e) {
        log.error(e.getMessage(), e);
        return "Object that you refer to does not exist or provided data format is incorrect. Check if provided data, specification and try again.";

    }

    @ExceptionHandler(RuntimeException.class)
    public final String handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return "Object that you refer to does not exist or provided data format is incorrect. Check if provided data, specification and try again.";

    }
}
