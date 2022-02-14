package com.cleverpy.domain.exceptions;

public class ActorInMovieException extends RuntimeException {
    private static final String DESCRIPTION = "Actor In Movie Exception";

    public ActorInMovieException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
