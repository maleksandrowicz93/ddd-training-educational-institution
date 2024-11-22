package com.github.maleksandrowicz93.edu.domain.library.bookLending;

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
    final BookInstanceId bookId;
    LocalDate to;
    LocalDate completionDate;
    ProlongPolicies prolongPolicies;
    @Getter
    boolean completed = false;
    @Getter
    int version = 0;

    private Lending(BookInstanceId bookInstanceId, Duration duration, ProlongPolicies prolongPolicies) {
        id = LendingId.newOne();
        this.bookId = bookInstanceId;
        to = LocalDate.now().plusDays(duration.toDays());
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
        if (completed) {
            return false;
        }
        var canBeProlonged = prolongPolicies.examine(duration);
        if (canBeProlonged) {
            to = to.plusDays(duration.toDays());
        }
        return canBeProlonged;
    }

    void complete() {
        if (!completed) {
            completed = true;
            completionDate = LocalDate.now();
            if (to.isAfter(completionDate)) {
                to = completionDate;
            }
        }
    }

    void updateProlongPolicies(ProlongPolicies prolongPolicies) {
        if (completed) {
            return;
        }
        this.prolongPolicies = prolongPolicies;
    }

    void incrementVersion() {
        version++;
    }

    boolean isCompleted() {
        return completed;
    }

    boolean isOverdue() {
        return completed
                ? completionDate.isAfter(to)
                : LocalDate.now().isAfter(to);
    }

    long overdueDays() {
        if (isOverdue()) {
            var duration = completed
                    ? Duration.between(completionDate, to)
                    : Duration.between(LocalDate.now(), to);
            return duration.toDays();
        }
        return 0;
    }
}
