package com.github.maleksandrowicz93.edu.domain.availability;

import java.util.UUID;

public record ResourceId(
        UUID value
) {

    static ResourceId newOne() {
        return new ResourceId(UUID.randomUUID());
    }
}
