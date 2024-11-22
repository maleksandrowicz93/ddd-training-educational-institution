package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookEditionId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;

import java.time.LocalDate;

record BookInstance(
        BookInstanceId id,
        BookEditionId bookEditionId,
        LocalDate foundingDate
) {

    static BookInstance of(BookEdition bookEdition) {
        return new BookInstance(BookInstanceId.newOne(), bookEdition.id(), LocalDate.now());
    }
}
