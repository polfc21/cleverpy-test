package com.cleverpy.entities;

public enum GenderType {

    MALE,
    FEMALE;

    public static boolean existsGender(String gender) {
        for (GenderType genderType: GenderType.values()) {
            if (genderType.name().equalsIgnoreCase(gender)) {
                return true;
            }
        }
        return false;
    }
}
