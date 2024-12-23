package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import java.util.UUID;

public record LendingId(
        UUID value
) {

    public static LendingId newOne() {
        return new LendingId(UUID.randomUUID());
    }
}
