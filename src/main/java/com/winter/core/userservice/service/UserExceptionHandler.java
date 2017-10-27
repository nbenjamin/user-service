package com.winter.core.userservice.service;

import com.winter.core.userservice.domain.CoreException;
import com.winter.core.userservice.domain.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class UserExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<?> handleUserException(CoreException e) {
        return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleDuplicateUserCreationFailure(final HttpServletRequest request,
                                                   DataIntegrityViolationException e) {
        LOGGER.info(request.getRequestURI());
        LOGGER.info("User already exist {}", e);

    }
}
