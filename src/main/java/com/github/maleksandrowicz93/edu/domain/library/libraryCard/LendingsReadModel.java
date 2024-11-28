package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LendingsReadModel {

    LendingRepo lendingRepo;

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
