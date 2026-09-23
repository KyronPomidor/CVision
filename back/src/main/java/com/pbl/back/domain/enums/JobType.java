package com.pbl.back.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum JobType {
    FULL_TIME_CONTRACT("Full-time Contract"),
    TEMPORARY_CONTRACT("Temporary Contract"),
    INTERNSHIP("Internship"),
    VOLUNTEER("Volunteer");

    private final String value;

    JobType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static JobType fromValue(String value) {
        for (JobType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown job type: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}