package com.cleverpy.domain.exceptions;

public class ParentRowException extends RuntimeException {
    private static final String DESCRIPTION = "Parent Row Exception";

    public ParentRowException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
