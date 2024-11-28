package com.github.maleksandrowicz93.edu.domain.library.prolongPolicies;

import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import lombok.Builder;

import java.time.Duration;
import java.time.LocalDate;

@Builder
public record ProlongationContext(
        LendingId lendingId,
        BookInstanceId bookInstanceId,
        LocalDate startDate,
        LocalDate dueDate,
        LocalDate completionDate,
        int prolongationCounter,
        Duration requestedDuration
) {

    public boolean isLendingCompleted() {
        return completionDate != null;
    }

    public boolean isLendingOverdue() {
        return isLendingCompleted()
                ? completionDate.isAfter(dueDate)
                : LocalDate.now().isAfter(dueDate);
    }

    public long overdueDays() {
        if (isLendingOverdue()) {
            var duration = isLendingCompleted()
                    ? Duration.between(completionDate, dueDate)
                    : Duration.between(LocalDate.now(), dueDate);
            return duration.toDays();
        }
        return 0;
    }
}
