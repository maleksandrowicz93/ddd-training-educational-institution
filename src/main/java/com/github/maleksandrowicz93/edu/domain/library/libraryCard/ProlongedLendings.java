package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import java.util.Collection;

public record ProlongedLendings(
        Collection<LendingId> ids
) {

    static ProlongedLendings of(Collection<LendingId> ids) {
        return new ProlongedLendings(ids);
    }

    public boolean empty() {
        return ids.isEmpty();
    }
}
