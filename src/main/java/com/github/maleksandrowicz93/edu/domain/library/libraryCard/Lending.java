package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongationContext;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Accessors(fluent = true)
@FieldDefaults(level = PRIVATE)
class Lending {

    private static final Duration DEFAULT_LENDING_DURATION = Duration.ofDays(14);

    @Getter
    final LendingId id;
    @Getter
    final BookInstanceId bookInstanceId;
    LocalDate dueDate;
    LocalDate completionDate;
    ProlongPolicies prolongPolicies;
    @Getter
    int prolongationCounter = 0;
    @Getter
    int version = 0;

    private Lending(BookInstanceId bookInstanceId, Duration duration, ProlongPolicies prolongPolicies) {
        id = LendingId.newOne();
        this.bookInstanceId = bookInstanceId;
        dueDate = LocalDate.now().plusDays(duration.toDays());
        this.prolongPolicies = prolongPolicies;
    }

    static Lending of(BookInstanceId bookInstanceId, ProlongPolicies prolongPolicies) {
        return new Lending(
                bookInstanceId,
                DEFAULT_LENDING_DURATION,
                prolongPolicies
        );
    }

    static Lending of(BookInstanceId bookInstanceId, ProlongPolicies prolongPolicies, Duration duration) {
        return new Lending(
                bookInstanceId,
                duration,
                prolongPolicies
        );
    }

    boolean prolongBook() {
        return prolongBook(DEFAULT_LENDING_DURATION);
    }

    boolean prolongBook(Duration duration) {
        if (isCompleted()) {
            return false;
        }
        var prolongationContext = prolongationContext(duration);
        var canBeProlonged = prolongPolicies.examine(prolongationContext);
        if (canBeProlonged) {
            dueDate = dueDate.plusDays(duration.toDays());
            prolongationCounter++;
        }
        return canBeProlonged;
    }

    void complete() {
        if (!isCompleted()) {
            completionDate = LocalDate.now();
            if (dueDate.isAfter(completionDate)) {
                dueDate = completionDate;
            }
        }
    }

    void updateProlongPolicies(ProlongPolicies prolongPolicies) {
        if (isCompleted()) {
            return;
        }
        this.prolongPolicies = prolongPolicies;
    }

    void incrementVersion() {
        version++;
    }

    boolean isCompleted() {
        return completionDate != null;
    }

    boolean isOverdue() {
        return isCompleted()
                ? completionDate.isAfter(dueDate)
                : LocalDate.now().isAfter(dueDate);
    }

    long overdueDays() {
        if (isOverdue()) {
            var duration = isCompleted()
                    ? Duration.between(completionDate, dueDate)
                    : Duration.between(LocalDate.now(), dueDate);
            return duration.toDays();
        }
        return 0;
    }

    private ProlongationContext prolongationContext(Duration duration) {
        return new ProlongationContext(
                id,
                bookInstanceId,
                dueDate,
                completionDate,
                prolongationCounter,
                duration
        );
    }
}
