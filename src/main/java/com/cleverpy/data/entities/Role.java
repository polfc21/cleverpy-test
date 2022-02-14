package com.cleverpy.data.entities;

public enum Role {

    ADMIN,
    CUSTOMER,
    AUTHENTICATED;

    public static final String PREFIX = "ROLE_";

    public String withPrefix() {
        return PREFIX + this.toString();
    }

}
