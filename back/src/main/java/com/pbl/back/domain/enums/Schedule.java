package com.pbl.back.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Schedule {
    FULL_TIME("Full-time"),
    PART_TIME("Part-time"),
    HYBRID("Hybrid"),
    FLEXIBLE("Flexible");

    private final String value;

    Schedule(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Schedule fromValue(String value) {
        for (Schedule schedule : values()) {
            if (schedule.value.equals(value)) {
                return schedule;
            }
        }

        throw new IllegalArgumentException("Unknown schedule: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}