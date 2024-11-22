package com.github.maleksandrowicz93.edu.domain.library.bookFunding;

import com.github.maleksandrowicz93.edu.domain.library.bookInventory.Book;

public record BookEditionCopies(
        Book book,
        int copiesNumber
) {

    public static BookEditionCopies singular(Book book) {
        return new BookEditionCopies(book, 1);
    }
}
