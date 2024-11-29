package com.github.maleksandrowicz93.edu.domain.library.bookDemand;

import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;

import java.util.Collection;

public interface BookDemandReadModel {

    Collection<ISBN> demandedBooks();

    boolean isDemanded(ISBN isbn);
}
