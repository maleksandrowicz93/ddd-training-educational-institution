package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.library.bookAccidents.BookAccidents;
import com.github.maleksandrowicz93.edu.domain.library.bookAvailability.BookAvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.BookInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingId;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingReadModel;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingRegistration;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingRequest;
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
public class BookLendingFacade {

    BookInventoryReadModel bookInventoryReadModel;
    LendingReadModel lendingReadModel;
    BookAvailabilityFacade bookAvailabilityFacade;
    LendingRegistration lendingRegistration;
    BookAccidents bookAccidents;

    @Transactional
    public Optional<LendingId> lenBook(ISBN isbn, ReaderId readerId) {
        var bookEditionId = bookInventoryReadModel.getBookEditionIdByIsbn(isbn);
        var takenBookInstanceId = bookAvailabilityFacade.takeAnyBookOf(bookEditionId, readerId);
        if (takenBookInstanceId.isEmpty()) {
            return Optional.empty();
        }
        var lendingId = new AtomicReference<LendingId>();
        takenBookInstanceId.ifPresent(
                bookId -> lendingRegistration.registerLending(readerId, bookId)
                                             .ifPresentOrElse(
                                                     lendingId::set,
                                                     () -> bookAvailabilityFacade.releaseBook(bookId, readerId)
                                             )
        );
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
        var lendingIds = lendingRegistration.registerBatchLending(readerId, takenBooks);
        if (lendingIds.isEmpty()) {
            bookAvailabilityFacade.releaseBooks(takenBooks, readerId);
            return Set.of();
        }
        return lendingIds;
    }

    @Transactional
    public void returnBook(LendingId lendingId, ReaderId readerId) {
        lendingRegistration.registerReturn(lendingId, readerId);
        var bookInstanceId = lendingReadModel.getBookInstanceIdOf(lendingId);
        bookAvailabilityFacade.releaseBook(bookInstanceId, readerId);
        if (lendingReadModel.isOverdue(lendingId)) {
            bookAccidents.registerOverdue(readerId);
        }
    }

    @Transactional
    public void returnBooks(Collection<LendingId> lendingIds, ReaderId readerId) {
        lendingRegistration.registerBatchReturn(lendingIds, readerId);
        var bookInstanceIds = lendingReadModel.findAllBookInstanceIdsOf(lendingIds);
        bookAvailabilityFacade.releaseBooks(bookInstanceIds, readerId);
        if (lendingReadModel.isAnyOverdue(lendingIds)) {
            bookAccidents.registerOverdue(readerId);
        }
    }
}
