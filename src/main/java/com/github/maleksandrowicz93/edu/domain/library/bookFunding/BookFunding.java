package com.github.maleksandrowicz93.edu.domain.library.bookFunding;

import com.github.maleksandrowicz93.edu.domain.library.bookInventory.Book;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.BookInventory;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.BookInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.library.readerScoring.ReaderScoring;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookFunding {

    BookInventoryReadModel bookInventoryReadModel;
    BookInventory bookInventory;
    ReaderScoring readerScoring;

    public void fundBook(Book book, ReaderId readerId) {
        var isbn = book.isbn();
        if (bookInventoryReadModel.isMissingInventoryFor(isbn)) {
            bookInventory.createInventoryFor(book);
        }
        bookInventory.addBookInstanceOf(isbn);
        readerScoring.addPoint(readerId);
    }

    public void fundBooks(BookEditionCopies bookEditionCopies, ReaderId readerId) {
        addBooksToInventory(bookEditionCopies);
        readerScoring.addPoints(readerId, bookEditionCopies.copiesNumber());
    }

    public void fundBooks(BooksBag booksBag, ReaderId readerId) {
        booksBag.editionSets()
                .forEach(this::addBooksToInventory);
        var points = booksBag.editionSets()
                             .stream()
                             .mapToInt(BookEditionCopies::copiesNumber)
                             .sum();
        readerScoring.addPoints(readerId, points);
    }

    private void addBooksToInventory(BookEditionCopies bookEditionCopies) {
        var book = bookEditionCopies.book();
        var isbn = book.isbn();
        if (bookInventoryReadModel.isMissingInventoryFor(isbn)) {
            bookInventory.createInventoryFor(book);
        }
        bookInventory.addBookInstancesOf(isbn, bookEditionCopies.copiesNumber());
    }
}
