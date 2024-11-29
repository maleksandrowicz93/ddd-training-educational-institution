package com.github.maleksandrowicz93.edu.domain.library;

import com.github.maleksandrowicz93.edu.domain.library.bookAccidents.BookAccidents;
import com.github.maleksandrowicz93.edu.domain.library.bookDemand.BookDemandScheduler;
import com.github.maleksandrowicz93.edu.domain.library.bookFunding.BookEditionCopies;
import com.github.maleksandrowicz93.edu.domain.library.bookFunding.BookFunding;
import com.github.maleksandrowicz93.edu.domain.library.bookFunding.BooksBag;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.Book;
import com.github.maleksandrowicz93.edu.domain.library.bookLending.BookLendingFacade;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingId;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingProlongation;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingRequest;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LibraryCardId;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.ProlongedLendings;
import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.readerRegistration.ReaderRegistration;
import com.github.maleksandrowicz93.edu.domain.library.readerRegistration.ReaderRegistrationRequest;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LibraryFacade {

    ReaderRegistration readerRegistration;
    BookFunding bookFunding;
    BookAccidents bookAccidents;
    BookLendingFacade bookLending;
    LendingProlongation lendingProlongation;
    BookDemandScheduler bookDemandScheduler;

    public Optional<LibraryCardId> addReader(ReaderRegistrationRequest readerRegistrationRequest) {
        return readerRegistration.registerNewOne(readerRegistrationRequest);
    }

    public boolean unregisterReader(ReaderId readerId) {
        return readerRegistration.unregisterReader(readerId);
    }

    public void fundBook(Book book, ReaderId readerId) {
        bookFunding.fundBook(book, readerId);
    }

    public void fundBooks(BookEditionCopies bookEditionCopies, ReaderId readerId) {
        bookFunding.fundBooks(bookEditionCopies, readerId);
    }

    public void fundBooks(BooksBag booksBag, ReaderId readerId) {
        bookFunding.fundBooks(booksBag, readerId);
    }

    public boolean removeBookFromLibrary(BookInstanceId bookInstanceId) {
        return bookAccidents.removeBookFromLibrary(bookInstanceId);
    }

    public void registerBookDamage(BookInstanceId bookInstanceId, ReaderId readerId) {
        bookAccidents.registerBookDamage(bookInstanceId, readerId);
    }

    public Optional<LendingId> lenBook(ISBN isbn, ReaderId readerId) {
        return bookLending.lenBook(isbn, readerId);
    }

    public Collection<LendingId> lenBook(LendingRequest lendingRequest) {
        return bookLending.lendBooks(lendingRequest);
    }

    public void lenBook(Collection<LendingId> lendingIds, ReaderId readerId) {
        bookLending.returnBooks(lendingIds, readerId);
    }

    public void returnBook(LendingId lendingId, ReaderId readerId) {
        bookLending.returnBook(lendingId, readerId);
    }

    public void returnBooks(Collection<LendingId> lendingIds, ReaderId readerId) {
        bookLending.returnBooks(lendingIds, readerId);
    }

    public boolean prolongLendings(LendingId lendingId) {
        return lendingProlongation.prolongLending(lendingId);
    }

    public boolean prolongLendings(LendingId lendingId, Duration duration) {
        return lendingProlongation.prolongLending(lendingId, duration);
    }

    public ProlongedLendings prolongLendings(Collection<LendingId> lendingIds) {
        return lendingProlongation.prolongLendings(lendingIds);
    }

    public ProlongedLendings prolongLendings(Collection<LendingId> lendingIds, Duration duration) {
        return lendingProlongation.prolongLendings(lendingIds, duration);
    }

    public void updateProlongPolicies(ReaderId readerId, ProlongPolicies prolongPolicies) {
        lendingProlongation.updateProlongPolicies(readerId, prolongPolicies);
    }

    public void scheduleDemandForBook(ReaderId readerId, ISBN isbn) {
        bookDemandScheduler.scheduleDemandFor(readerId, isbn);
    }

    public void revokeDemandForBook(ReaderId readerId, ISBN isbn) {
        bookDemandScheduler.revokeDemandFor(readerId, isbn);
    }
}
