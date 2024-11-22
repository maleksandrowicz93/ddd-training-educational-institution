package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

import java.util.Collection;

public class LendingReadModel {

    LibraryCardRepo libraryCardRepo;

    public Collection<LendingId> findActiveLendingIdsOf(ReaderId readerId) {
        return libraryCardRepo.findByReaderId(readerId)
                              .map(LibraryCard::lendings)
                              .stream()
                              .flatMap(Collection::stream)
                              .toList();
    }

}
