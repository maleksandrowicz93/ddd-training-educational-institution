package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import com.github.maleksandrowicz93.edu.common.infra.OptimisticLockingException;

import java.util.Collection;
import java.util.Optional;

interface LendingRepo {

    void saveNew(Lending lending);

    void saveNew(Lendings lendings);

    void saveCheckingVersion(Lending lending) throws OptimisticLockingException;

    void saveCheckingVersion(Lendings lendings) throws OptimisticLockingException;

    void deleteAllByIds(Collection<LendingId> ids);

    Lendings findAllByIds(Collection<LendingId> ids);

    Optional<Lending> findById(LendingId id);

    default Lending getById(LendingId id) {
        return findById(id).orElseThrow();
    }
}
