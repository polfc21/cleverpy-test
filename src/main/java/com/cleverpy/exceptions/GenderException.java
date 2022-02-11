package com.cleverpy.exceptions;

public class GenderException extends RuntimeException {

    private static final String DESCRIPTION = "Gender Exception";

    public GenderException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
