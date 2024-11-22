package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookEditionId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;

import java.util.Collection;
import java.util.Optional;

interface BookEditionCatalog {

    void save(BookEdition bookEdition);

    boolean doesNotExistEntryWIth(ISBN isbn);

    Optional<BookEditionId> findIdByIsbn(ISBN isbn);

    Collection<BookEditionId> findAllIdsByIsbns(Collection<ISBN> isbns);

    Optional<BookEdition> findByIsbn(ISBN isbn);

    default BookEdition getByIsbn(ISBN isbn) {
        return findByIsbn(isbn).orElseThrow();
    }
}
