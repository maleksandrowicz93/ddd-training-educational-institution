package com.github.maleksandrowicz93.edu.domain.library.shared;

import java.util.UUID;

public record BookInstanceId(
        UUID value
) {

    public static BookInstanceId newOne() {
        return new BookInstanceId(UUID.randomUUID());
    }
}
