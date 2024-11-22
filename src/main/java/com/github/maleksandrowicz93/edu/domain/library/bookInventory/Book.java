package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;

public record Book(
        ISBN isbn,
        Title title,
        Authors authors
) {
}
