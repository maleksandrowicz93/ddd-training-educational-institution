package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.common.infra.OptimisticLockingException;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

import java.util.Optional;

interface LibraryCardRepo {

    void saveNew(LibraryCard libraryCard);

    void saveCheckingVersion(LibraryCard libraryCard) throws OptimisticLockingException;

    void deleteByReaderId(ReaderId readerId);

    boolean existsByReaderId(ReaderId readerId);

    Optional<LibraryCard> findById(LibraryCardId id);

    Optional<LibraryCard> findByReaderId(ReaderId readerId);

    default LibraryCard getById(LibraryCardId id) {
        return findById(id).orElseThrow();
    }

    default LibraryCard getByReaderId(ReaderId readerId) {
        return findByReaderId(readerId).orElseThrow();
    }
}
