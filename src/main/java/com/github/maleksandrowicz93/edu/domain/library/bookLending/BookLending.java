package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.library.bookAvailability.BookAvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.BookInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookLending {

    BookAvailabilityFacade bookAvailabilityFacade;
    BookInventoryReadModel bookInventoryReadModel;
    LibraryCardRepo libraryCardRepo;
    LendingRepo lendingRepo;

    @Transactional
    public Optional<LendingId> lenBook(ISBN isbn, ReaderId readerId) {
        var bookEditionId = bookInventoryReadModel.getBookEditionIdByIsbn(isbn);
        var takenBookInstanceId = bookAvailabilityFacade.takeAnyBookOf(bookEditionId, readerId);
        if (takenBookInstanceId.isEmpty()) {
            return Optional.empty();
        }
        var lendingId = new AtomicReference<LendingId>();
        takenBookInstanceId.ifPresent(bookInstanceId -> {
            var libraryCard = libraryCardRepo.getByReaderId(readerId);
            var lendings = libraryCard.lendBook(bookInstanceId, ProlongPolicies.EMPTY);
            if (lendings.empty()) {
                bookAvailabilityFacade.releaseBook(bookInstanceId, readerId);
                return;
            }
            var lending = lendings.first();
            libraryCardRepo.saveCheckingVersion(libraryCard);
            lendingRepo.saveNew(lending);
            lendingId.set(lending.id());
        });
        return Optional.ofNullable(lendingId.get());
    }

    @Transactional
    public Collection<LendingId> lendBooks(LendingRequest lendingRequest) {
        var bookEditionIds = bookInventoryReadModel.findAllBookEditionIdsByIsbns(lendingRequest.isbns());
        var readerId = lendingRequest.readerId();
        var takenBooks = bookEditionIds.stream()
                                       .map(editionId -> bookAvailabilityFacade.takeAnyBookOf(editionId, readerId))
                                       .flatMap(Optional::stream)
                                       .collect(toSet());
        if (takenBooks.isEmpty()) {
            return Set.of();
        }
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        var lendings = libraryCard.lendBooks(takenBooks, ProlongPolicies.EMPTY);
        if (lendings.empty()) {
            bookAvailabilityFacade.releaseBooks(takenBooks, readerId);
            return Set.of();
        }
        libraryCardRepo.saveCheckingVersion(libraryCard);
        lendingRepo.saveNew(lendings);
        return lendings.ids();
    }

    @Transactional
    public void returnBook(LendingId lendingId, ReaderId readerId) {
        var lending = lendingRepo.getById(lendingId);
        lending.complete();
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        libraryCard.returnBook(lendingId);
        libraryCardRepo.saveCheckingVersion(libraryCard);
        lendingRepo.saveCheckingVersion(lending);
        bookAvailabilityFacade.releaseBook(lending.bookId(), readerId);
    }

    @Transactional
    public void returnBooks(Collection<LendingId> lendingIds, ReaderId readerId) {
        var lendings = lendingRepo.findAllByIds(lendingIds);
        var lentBooks = lendings.bookIds();
        lendings.complete();
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        libraryCard.returnBooks(lendingIds);
        libraryCardRepo.saveCheckingVersion(libraryCard);
        lendingRepo.saveCheckingVersion(lendings);
        bookAvailabilityFacade.releaseBooks(lentBooks, readerId);
    }
}
