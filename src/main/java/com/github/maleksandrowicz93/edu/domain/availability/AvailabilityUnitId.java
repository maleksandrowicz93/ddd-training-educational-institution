package com.github.maleksandrowicz93.edu.domain.availability;

import java.util.UUID;

record AvailabilityUnitId(
        UUID value
) {

    static AvailabilityUnitId newOne() {
        return new AvailabilityUnitId(UUID.randomUUID());
    }
}
