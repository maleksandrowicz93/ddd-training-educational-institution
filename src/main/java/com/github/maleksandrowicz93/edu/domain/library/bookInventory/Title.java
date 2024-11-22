package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

public record Title(
        String value
) {

    public Title {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
}
