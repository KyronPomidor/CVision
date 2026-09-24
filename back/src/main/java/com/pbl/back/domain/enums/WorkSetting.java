package com.pbl.back.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WorkSetting {
    REMOTE("Remote"),
    ON_SITE("On-site"),
    HYBRID("Hybrid");

    private final String value;

    WorkSetting(String value) {
        this.value = value;
    }

    @JsonCreator
    public static WorkSetting fromValue(String value) {
        for (WorkSetting setting : values()) {
            if (setting.value.equals(value)) {
                return setting;
            }
        }

        throw new IllegalArgumentException("Unknown work setting: " + value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}