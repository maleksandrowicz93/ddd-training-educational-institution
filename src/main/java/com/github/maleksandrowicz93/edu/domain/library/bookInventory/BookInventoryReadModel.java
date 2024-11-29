package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.domain.library.bookAvailability.BookAvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookEditionId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookInventoryReadModel {

    BookEditionCatalog bookEditionCatalog;
    BookAvailabilityReadModel bookAvailabilityReadModel;

    public boolean isMissingInventoryFor(ISBN isbn) {
        return bookEditionCatalog.doesNotExistEntryWIth(isbn);
    }

    public Collection<BookEditionId> findAllBookEditionIdsByIsbns(Collection<ISBN> isbns) {
        return bookEditionCatalog.findAllIdsByIsbns(isbns);
    }

    public Optional<BookEditionId> findBookEditionIdByIsbn(ISBN isbn) {
        return bookEditionCatalog.findIdByIsbn(isbn);
    }

    public BookEditionId getBookEditionIdByIsbn(ISBN isbn) {
        return findBookEditionIdByIsbn(isbn).orElseThrow();
    }

    public Optional<ISBN> findIsbnByBookEditionId(BookEditionId bookEditionId) {
        return bookEditionCatalog.findIsbnByBookEditionId(bookEditionId);
    }

    public Optional<ISBN> findIsbnByBookInstanceId(BookInstanceId bookInstanceId) {
        return bookAvailabilityReadModel.findBookEditionIdByBookInstanceId(bookInstanceId)
                                        .flatMap(this::findIsbnByBookEditionId);
    }

    public ISBN getIsbnByBookInstanceId(BookInstanceId bookInstanceId) {
        return findIsbnByBookInstanceId(bookInstanceId).orElseThrow();
    }
}
