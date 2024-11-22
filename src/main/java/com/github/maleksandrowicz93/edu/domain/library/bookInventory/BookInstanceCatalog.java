package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;

import java.util.Collection;
import java.util.Optional;

interface BookInstanceCatalog {

    void save(BookInstance bookInstance);

    void saveAll(Collection<BookInstance> bookInstances);

    void deleteById(BookInstanceId id);

    void deleteAllByIds(Collection<BookInstanceId> bookInstanceIds);

    Optional<BookInstance> findById(BookInstanceId id);

    default BookInstance getById(BookInstanceId id) {
        return findById(id).orElseThrow();
    }
}
