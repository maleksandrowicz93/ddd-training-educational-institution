package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

import java.util.Collection;
import java.util.Optional;

public class LendingsReadModel {

    LibraryCardRepo libraryCardRepo;
    LendingRepo lendingRepo;

    public Collection<LendingId> findActiveLendingIdsOf(ReaderId readerId) {
        return libraryCardRepo.findByReaderId(readerId)
                              .map(LibraryCard::lendings)
                              .stream()
                              .flatMap(Collection::stream)
                              .toList();
    }

    public Collection<BookInstanceId> findAllBookInstanceIdsOf(Collection<LendingId> lendingIds) {
        return lendingRepo.findAllByIds(lendingIds)
                          .bookIds();
    }

    public Optional<BookInstanceId> findBookInstanceIdOf(LendingId lendingId) {
        return lendingRepo.findById(lendingId)
                          .map(Lending::bookInstanceId);
    }

    public BookInstanceId getBookInstanceIdOf(LendingId lendingId) {
        return findBookInstanceIdOf(lendingId).orElseThrow();
    }

    public boolean isOverdue(LendingId lendingId) {
        return lendingRepo.findById(lendingId)
                          .map(Lending::isOverdue)
                          .orElse(false);
    }

    public boolean isAnyOverdue(Collection<LendingId> lendingIds) {
        return lendingRepo.findAllByIds(lendingIds)
                          .isAnyOverdue();
    }
}
