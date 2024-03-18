package com.naman.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
public class NotFoundException extends Exception {

    

    public NotFoundException(String message) {
        super(message);
    }
}
