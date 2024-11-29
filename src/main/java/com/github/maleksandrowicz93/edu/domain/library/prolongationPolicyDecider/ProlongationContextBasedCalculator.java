package com.github.maleksandrowicz93.edu.domain.library.prolongationPolicyDecider;

import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongationContext;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderCatalog;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicy.not;
import static java.time.temporal.ChronoUnit.YEARS;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class ProlongationContextBasedCalculator implements ProlongationPolicyCalculator {

    ReaderCatalog readerCatalog;

    @Override
    public ProlongPolicies calculatePoliciesFor(ReaderId readerId) {
        return switch (readerCatalog.getReaderTypeById(readerId)) {
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
