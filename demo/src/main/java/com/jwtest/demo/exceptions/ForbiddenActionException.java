package com.jwtest.demo.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@ResponseStatus(code = FORBIDDEN, reason = "Forbidden action")
public class ForbiddenActionException extends RuntimeException {
}
