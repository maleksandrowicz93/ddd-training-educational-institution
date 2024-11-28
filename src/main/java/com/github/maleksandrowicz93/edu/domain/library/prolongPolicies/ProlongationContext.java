package com.github.maleksandrowicz93.edu.domain.library.prolongPolicies;

import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LendingId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;

import java.time.Duration;
import java.time.LocalDate;

public record ProlongationContext(
        LendingId id,
        BookInstanceId bookInstanceId,
        LocalDate dueDate,
        LocalDate completionDate,
        int prolongationCounter,
        Duration duration
) {
}
