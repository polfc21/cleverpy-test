package com.cleverpy.exceptions;

public class FilmGenreException extends RuntimeException {

    private static final String DESCRIPTION = "Film Genre Exception";

    public FilmGenreException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
