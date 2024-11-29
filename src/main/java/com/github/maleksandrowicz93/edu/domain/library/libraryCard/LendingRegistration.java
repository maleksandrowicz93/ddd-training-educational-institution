package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.library.prolongationPolicyDecider.ProlongationPolicyDecider;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LendingRegistration {

    LibraryCardRepo libraryCardRepo;
    LendingRepo lendingRepo;
    ProlongationPolicyDecider prolongationPolicyDecider;

    @Transactional
    public Optional<LendingId> registerLending(ReaderId readerId, BookInstanceId bookInstanceId) {
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        var prolongPolicies = prolongationPolicyDecider.choosePoliciesFor(readerId);
        var maybeLending = libraryCard.lendBook(bookInstanceId, prolongPolicies);
        maybeLending.ifPresent(lending -> {
            libraryCardRepo.saveCheckingVersion(libraryCard);
            lendingRepo.saveNew(lending);
        });
        return maybeLending.map(Lending::id);
    }

    @Transactional
    public Collection<LendingId> registerBatchLending(ReaderId readerId, Collection<BookInstanceId> bookInstanceIds) {
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        var prolongPolicies = prolongationPolicyDecider.choosePoliciesFor(readerId);
        var lendings = libraryCard.lendBooks(bookInstanceIds, prolongPolicies);
        if (!lendings.empty()) {
            libraryCardRepo.saveCheckingVersion(libraryCard);
            lendingRepo.saveNew(lendings);
        }
        return lendings.ids();
    }

    @Transactional
    public void registerReturn(LendingId lendingId, ReaderId readerId) {
        var lending = lendingRepo.getById(lendingId);
        lending.complete();
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        libraryCard.returnBook(lendingId);
        libraryCardRepo.saveCheckingVersion(libraryCard);
        lendingRepo.saveCheckingVersion(lending);
    }

    @Transactional
    public void registerBatchReturn(Collection<LendingId> lendingIds, ReaderId readerId) {
        var lendings = lendingRepo.findAllByIds(lendingIds);
        lendings.complete();
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        libraryCard.returnBooks(lendingIds);
        libraryCardRepo.saveCheckingVersion(libraryCard);
        lendingRepo.saveCheckingVersion(lendings);
    }
}
