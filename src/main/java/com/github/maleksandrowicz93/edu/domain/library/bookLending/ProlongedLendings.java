package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import java.util.Collection;
import java.util.Set;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

public record ProlongedLendings(
        Collection<LendingId> ids
) {

    static ProlongedLendings of(Collection<LendingId> ids) {
        return new ProlongedLendings(ids);
    }

    public boolean empty() {
        return ids.isEmpty();
    }

    public Collection<LendingId> findMissingFor(Collection<LendingId> lendingIds) {
        if (ids.containsAll(lendingIds)) {
            return Set.of();
        }
        return lendingIds.stream()
                         .filter(not(ids::contains))
                         .collect(toSet());
    }
}
