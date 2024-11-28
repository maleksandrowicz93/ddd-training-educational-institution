package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class LibraryCardReadModel {

    LibraryCardRepo libraryCardRepo;

    public Collection<LendingId> findActiveLendingIdsOf(ReaderId readerId) {
        return libraryCardRepo.findByReaderId(readerId)
                              .map(LibraryCard::lendings)
                              .stream()
                              .flatMap(Collection::stream)
                              .toList();
    }
}
