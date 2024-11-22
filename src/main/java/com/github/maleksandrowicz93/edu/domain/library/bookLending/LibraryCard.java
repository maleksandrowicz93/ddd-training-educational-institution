package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@Accessors(fluent = true)
@FieldDefaults(level = PRIVATE)
class LibraryCard {

    private static final int DEFAULT_BORROWS_LIMIT = 5;

    @Getter
    final LibraryCardId id;
    @Getter
    final ReaderId readerId;
    final Collection<LendingId> lendings = new HashSet<>();
    int limit;
    @Getter
    int version = 0;

    private LibraryCard(LibraryCardId id, ReaderId readerId, int limit) {
        this.id = id;
        this.readerId = readerId;
        this.limit = limit;
    }

    static LibraryCard newOne(ReaderId readerId) {
        return new LibraryCard(LibraryCardId.newOne(), readerId, DEFAULT_BORROWS_LIMIT);
    }

    static LibraryCard newOne(ReaderId readerId, int limit) {
        return new LibraryCard(LibraryCardId.newOne(), readerId, limit);
    }

    Lendings lendBook(
            BookInstanceId bookInstanceId,
            ProlongPolicies prolongPolicies
    ) {
        return lendBook(
                () -> Lendings.singular(bookInstanceId, prolongPolicies)
        );
    }

    Lendings lendBook(
            BookInstanceId bookInstanceId,
            ProlongPolicies prolongPolicies,
            Duration duration
    ) {
        return lendBook(
                () -> Lendings.singular(bookInstanceId, prolongPolicies, duration)
        );
    }

    Lendings lendBooks(
            Collection<BookInstanceId> bookInstanceIds,
            ProlongPolicies prolongPolicies
    ) {
        return lendBooks(
                () -> Lendings.from(bookInstanceIds, prolongPolicies)
        );
    }

    Lendings lendBooks(
            Collection<BookInstanceId> bookInstanceIds,
            ProlongPolicies prolongPolicies,
            Duration duration
    ) {
        return lendBooks(
                () -> Lendings.from(bookInstanceIds, prolongPolicies, duration)
        );
    }

    private Lendings lendBook(
            Supplier<Lendings> lendingsFactory
    ) {
        if (lendings.size() >= limit) {
            return Lendings.EMPTY;
        }
        var newLendings = lendingsFactory.get();
        lendings.addAll(newLendings.ids());
        return newLendings;
    }

    private Lendings lendBooks(
            Supplier<Lendings> lendingsFactory
    ) {
        var newLendings = lendingsFactory.get();
        if (lendings.size() + newLendings.count() >= limit) {
            return Lendings.EMPTY;
        }
        lendings.addAll(newLendings.ids());
        return newLendings;
    }

    void returnBook(LendingId lendingId) {
        lendings.remove(lendingId);
    }

    void returnBooks(Collection<LendingId> lendingIds) {
        lendings.removeAll(lendingIds);
    }

    void changeLimit(int limit) {
        if (limit >= 0) {
            this.limit = limit;
        }
    }

    void incrementVersion() {
        version++;
    }

    Collection<LendingId> lendings() {
        return Set.copyOf(lendings);
    }
}
