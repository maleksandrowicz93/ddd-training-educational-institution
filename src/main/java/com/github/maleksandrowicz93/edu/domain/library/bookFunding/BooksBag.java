package com.github.maleksandrowicz93.edu.domain.library.bookFunding;

import java.util.Collection;

public record BooksBag(
        Collection<BookEditionCopies> editionSets
) {
}
