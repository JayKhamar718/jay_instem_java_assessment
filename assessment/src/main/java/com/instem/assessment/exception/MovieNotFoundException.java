package com.instem.assessment.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(final String message) {
        super(message);
    }
}
