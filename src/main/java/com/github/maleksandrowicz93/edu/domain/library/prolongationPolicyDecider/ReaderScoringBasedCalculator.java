package com.github.maleksandrowicz93.edu.domain.library.prolongationPolicyDecider;

import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderCatalog;
import com.github.maleksandrowicz93.edu.domain.library.readerScoring.ReaderScoringReadModel;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class ReaderScoringBasedCalculator implements ProlongationPolicyCalculator {

    ReaderCatalog readerCatalog;
    ReaderScoringReadModel readerScoringReadModel;

    @Override
    public ProlongPolicies calculatePoliciesFor(ReaderId readerId) {
        return switch (readerCatalog.getReaderTypeById(readerId)) {
            case STUDENT, FUNDER -> ProlongPolicies.from()
                                                   .policy(_ -> readerScoringReadModel.scoreOf(readerId).isPositive())
                                                   .compose();
            case PROFESSOR -> ProlongPolicies.from()
                                             .policy(_ -> readerScoringReadModel.scoreOf(readerId).value() >= -3)
                                             .compose();
            case VIP -> ProlongPolicies.EMPTY;
        };
    }
}
