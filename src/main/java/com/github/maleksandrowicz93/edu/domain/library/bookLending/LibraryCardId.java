package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import java.util.UUID;

public record LibraryCardId(
        UUID value
) {

    public static LibraryCardId newOne() {
        return new LibraryCardId(UUID.randomUUID());
    }
}
