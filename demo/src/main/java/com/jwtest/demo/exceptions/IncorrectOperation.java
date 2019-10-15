package com.jwtest.demo.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(code = BAD_REQUEST, reason = "Incorrect operation")
public class IncorrectOperation extends RuntimeException {
}
