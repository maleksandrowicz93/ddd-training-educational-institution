package com.github.maleksandrowicz93.edu.domain.library.bookAccident;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;

import java.time.LocalDate;

record BookDamage(
        BookInstanceId bookInstanceId,
        LocalDate occurrenceTime
) {

    static BookDamage of(BookInstanceId bookInstanceId) {
        return new BookDamage(bookInstanceId, LocalDate.now());
    }
}
