package com.github.maleksandrowicz93.edu.domain.library.shared;

import java.util.UUID;

public record ReaderId(
        UUID value
) {

    public static ReaderId newOne() {
        return new ReaderId(UUID.randomUUID());
    }
}
