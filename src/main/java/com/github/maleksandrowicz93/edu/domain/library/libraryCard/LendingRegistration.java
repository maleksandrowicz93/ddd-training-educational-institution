package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongationContext;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderCatalog;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;

import static com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicy.not;
import static java.time.temporal.ChronoUnit.YEARS;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LendingRegistration {

    LibraryCardRepo libraryCardRepo;
    LendingRepo lendingRepo;
    ReaderCatalog readerCatalog;

    @Transactional
    public Optional<LendingId> registerLending(ReaderId readerId, BookInstanceId bookInstanceId) {
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        var maybeLending = libraryCard.lendBook(bookInstanceId, assumePolicies(readerId));
        maybeLending.ifPresent(lending -> {
            libraryCardRepo.saveCheckingVersion(libraryCard);
            lendingRepo.saveNew(lending);
        });
        return maybeLending.map(Lending::id);
    }

    @Transactional
    public Collection<LendingId> registerBatchLending(ReaderId readerId, Collection<BookInstanceId> bookInstanceIds) {
        var libraryCard = libraryCardRepo.getByReaderId(readerId);
        var lendings = libraryCard.lendBooks(bookInstanceIds, assumePolicies(readerId));
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

    //what if special book, only for professor and vip
    //what if professor scheduled demand
    //what if policy depends on scoring
    //what if...
    private ProlongPolicies assumePolicies(ReaderId readerId) {
        var readerType = readerCatalog.getReaderTypeById(readerId);
        return switch (readerType) {
            case STUDENT -> ProlongPolicies.from()
                                           .policy(not(ProlongationContext::isLendingOverdue))
                                           .policy(context -> context.prolongationCounter() < 3)
                                           .policy(context -> context.requestedDuration().toDays() <= 14)
                                           .compose();
            case PROFESSOR -> ProlongPolicies.from()
                                             .policy(context -> context.overdueDays() <= 30)
                                             .policy(context -> context.requestedDuration().toDays() < 100)
                                             .policy(context -> YEARS.between(context.startDate(), context.dueDate()) < 1)
                                             .compose();
            case FUNDER -> ProlongPolicies.from()
                                          .policy(context -> context.overdueDays() <= 30)
                                          .policy(context -> context.prolongationCounter() < 3)
                                          .policy(context -> context.requestedDuration().toDays() <= 30)
                                          .compose();
            case VIP -> ProlongPolicies.EMPTY;
        };
    }
}
