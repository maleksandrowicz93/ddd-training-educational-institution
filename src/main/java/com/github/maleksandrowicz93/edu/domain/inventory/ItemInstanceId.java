package com.github.maleksandrowicz93.edu.domain.inventory;

import java.util.UUID;

public record ItemInstanceId(
        UUID value
) {

    public static ItemInstanceId newOne() {
        return new ItemInstanceId(UUID.randomUUID());
    }
}
