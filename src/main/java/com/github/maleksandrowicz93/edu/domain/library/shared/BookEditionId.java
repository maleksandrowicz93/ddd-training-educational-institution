package com.github.maleksandrowicz93.edu.domain.library.shared;

import java.util.UUID;

public record BookEditionId(
        UUID value
) {

    public static BookEditionId newOne() {
        return new BookEditionId(UUID.randomUUID());
    }
}
