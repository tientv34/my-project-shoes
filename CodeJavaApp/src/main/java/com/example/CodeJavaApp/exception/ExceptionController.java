package com.example.CodeJavaApp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public String handleProductNotException(Exception exception){
        exception.printStackTrace();
        return "exception";
    }
}
