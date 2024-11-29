package com.github.maleksandrowicz93.edu.domain.library.bookDemand;

import com.github.maleksandrowicz93.edu.domain.library.shared.ISBN;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

public interface BookDemandScheduler {

    void scheduleDemandFor(ReaderId readerId, ISBN isbn);

    void revokeDemandFor(ReaderId readerId, ISBN bookEditionId);
}
