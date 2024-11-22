package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookEditionId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;

import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

record BookEdition(
        BookEditionId id,
        ISBN isbn,
        Title title,
        Authors authors
) {

    private BookEdition(ISBN isbn, Title title, Authors authors) {
        this(BookEditionId.newOne(), isbn, title, authors);
    }

    static BookEdition of(Book book) {
        return new BookEdition(book.isbn(), book.title(), book.authors());
    }

    BookInstance createInstance() {
        return BookInstance.of(this);
    }

    Collection<BookInstance> createInstances(int number) {
        return IntStream.rangeClosed(1, number)
                        .mapToObj(_ -> createInstance())
                        .collect(toSet());
    }
}
