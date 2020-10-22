package com.jerry.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020-10-22
 * Time: 10:38
 * Description:
 */
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handle(Exception e) {
        Map<String, Object> info = new HashMap<>();
        info.put("message", e.getMessage());
        info.put("time", new Date().getTime());
        return info;
    }

}
