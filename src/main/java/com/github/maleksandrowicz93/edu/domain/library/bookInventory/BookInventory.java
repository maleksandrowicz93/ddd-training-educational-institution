package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.domain.library.bookAvailability.BookAvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookInventory {

    BookEditionCatalog bookEditionCatalog;
    BookInstanceCatalog bookInstanceCatalog;
    BookAvailabilityFacade bookAvailabilityFacade;

    public void createInventoryFor(Book book) {
        if (bookEditionCatalog.doesNotExistEntryWIth(book.isbn())) {
            bookEditionCatalog.save(BookEdition.of(book));
        }
    }

    public void addBookInstanceOf(ISBN isbn) {
        var bookEdition = bookEditionCatalog.getByIsbn(isbn);
        var bookInstance = bookEdition.createInstance();
        bookInstanceCatalog.save(bookInstance);
        bookAvailabilityFacade.createBookAvailabilityUnit(bookEdition.id(), bookInstance.id());
    }

    public void addBookInstancesOf(ISBN isbn, int number) {
        var bookEdition = bookEditionCatalog.getByIsbn(isbn);
        var bookInstances = bookEdition.createInstances(number);
        var bookInstanceIds = bookInstances.stream()
                                           .map(BookInstance::id)
                                           .collect(toSet());
        bookInstanceCatalog.saveAll(bookInstances);
        bookAvailabilityFacade.createBookAvailabilityUnits(bookEdition.id(), bookInstanceIds);
    }

    public void removeBookInstance(BookInstanceId id) {
        bookInstanceCatalog.deleteById(id);
        bookAvailabilityFacade.deleteBookAvailabilityUnit(id);
    }
}
