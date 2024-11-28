package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

import java.util.Collection;

public record LendingRequest(
        ReaderId readerId,
        Collection<ISBN> isbns
) {
}
