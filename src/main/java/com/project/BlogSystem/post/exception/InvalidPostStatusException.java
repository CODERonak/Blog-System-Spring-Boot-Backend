
package com.project.BlogSystem.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPostStatusException extends RuntimeException {
    public InvalidPostStatusException(String message) {
        super(message);
    }
}
