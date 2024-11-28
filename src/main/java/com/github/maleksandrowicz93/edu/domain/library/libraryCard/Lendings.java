package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class Lendings {

    static final Lendings EMPTY = new Lendings(Set.of());

    Collection<Lending> all;

    static Lendings from(
            Collection<BookInstanceId> bookInstanceIds,
            ProlongPolicies prolongPolicies
    ) {
        return new Lendings(
                bookInstanceIds.stream()
                               .map(bookId -> Lending.of(bookId, prolongPolicies))
                               .collect(toSet())
        );
    }

    static Lendings from(
            Collection<BookInstanceId> bookInstanceIds,
            ProlongPolicies prolongPolicies,
            Duration duration
    ) {
        return new Lendings(
                bookInstanceIds.stream()
                               .map(bookId -> Lending.of(bookId, prolongPolicies, duration))
                               .collect(toSet())
        );
    }

    ProlongedLendings prolong() {
        return prolong(Lending::prolongBook);
    }

    ProlongedLendings prolong(Duration duration) {
        return prolong(lending -> lending.prolongBook(duration));
    }

    private ProlongedLendings prolong(Predicate<Lending> prolongationTry) {
        var prolongedLendingIds = new HashSet<LendingId>();
        for (var lending : all) {
            var prolonged = prolongationTry.test(lending);
            if (prolonged) {
                prolongedLendingIds.add(lending.id());
            }
        }
        return ProlongedLendings.of(prolongedLendingIds);
    }

    void complete() {
        all.forEach(Lending::complete);
    }

    void updateProlongPolicies(ProlongPolicies prolongPolicies) {
        all.forEach(lending -> lending.updateProlongPolicies(prolongPolicies));
    }

    Lendings filterBy(Collection<LendingId> lendingIds) {
        return all.stream()
                  .filter(lending -> lendingIds.contains(lending.id()))
                  .collect(collectingAndThen(
                          toSet(),
                          Lendings::new
                  ));
    }

    Collection<LendingId> ids() {
        return all.stream()
                  .map(Lending::id)
                  .collect(toSet());
    }

    Collection<BookInstanceId> bookIds() {
        return all.stream()
                  .map(Lending::bookInstanceId)
                  .collect(toSet());
    }

    int count() {
        return all.size();
    }

    boolean empty() {
        return all.isEmpty();
    }

    boolean isAnyOverdue() {
        return all.stream()
                  .anyMatch(Lending::isOverdue);
    }
}
