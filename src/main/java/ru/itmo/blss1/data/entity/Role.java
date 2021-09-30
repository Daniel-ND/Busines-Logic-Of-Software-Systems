package ru.itmo.blss1.data.entity;

import static java.lang.String.format;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    @JsonCreator
    public static Role forValue(final String value) {
        for (final var role : values()) {
            if (role.toString().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException(format("No role with text %s found.", value));
    }
}