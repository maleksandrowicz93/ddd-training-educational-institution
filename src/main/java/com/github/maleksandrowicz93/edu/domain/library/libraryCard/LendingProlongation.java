package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LendingProlongation {

    LendingReadModel lendingReadModel;
    LendingRepo lendingRepo;

    public boolean prolongLending(LendingId lendingId) {
        return prolongLending(lendingId, Lending::prolongBook);
    }

    public boolean prolongLending(LendingId lendingId, Duration duration) {
        return prolongLending(
                lendingId,
                lending -> lending.prolongBook(duration)
        );
    }

    public ProlongedLendings prolongLendings(Collection<LendingId> lendingIds) {
        return prolongLendings(lendingIds, Lendings::prolong);
    }

    public ProlongedLendings prolongLendings(Collection<LendingId> lendingIds, Duration duration) {
        return prolongLendings(
                lendingIds,
                lendings -> lendings.prolong(duration)
        );
    }

    public void updateProlongPolicies(ReaderId readerId, ProlongPolicies prolongPolicies) {
        var lendingIds = lendingReadModel.findActiveLendingIdsOf(readerId);
        var lendings = lendingRepo.findAllByIds(lendingIds);
        lendings.updateProlongPolicies(prolongPolicies);
        lendingRepo.saveCheckingVersion(lendings);
    }

    private boolean prolongLending(
            LendingId lendingId,
            Predicate<Lending> prolongationAction
    ) {
        var lending = lendingRepo.getById(lendingId);
        var prolonged = prolongationAction.test(lending);
        if (prolonged) {
            lendingRepo.saveCheckingVersion(lending);
        }
        return prolonged;
    }

    private ProlongedLendings prolongLendings(
            Collection<LendingId> lendingIds,
            Function<Lendings, ProlongedLendings> prolongationAction
    ) {
        var lendings = lendingRepo.findAllByIds(lendingIds);
        var prolongedLendings = prolongationAction.apply(lendings);
        if (!prolongedLendings.empty()) {
            var changedLendings = lendings.filterBy(prolongedLendings.ids());
            lendingRepo.saveCheckingVersion(changedLendings);
        }
        return prolongedLendings;
    }
}
