package com.github.maleksandrowicz93.edu.domain.availability;

import java.util.UUID;

public record OwnerId(
        UUID value
) {

    public static OwnerId newOne() {
        return new OwnerId(UUID.randomUUID());
    }
}
