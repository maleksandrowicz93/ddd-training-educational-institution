package com.github.maleksandrowicz93.edu.domain.library.prolongationPolicyDecider;

import com.github.maleksandrowicz93.edu.domain.library.bookDemand.BookDemandReadModel;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.BookInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderCatalog;
import com.github.maleksandrowicz93.edu.domain.library.readerScoring.ReaderScoringReadModel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProlongationPolicyDeciderFactory {

    ReaderCatalog readerCatalog;
    ReaderScoringReadModel readerScoringReadModel;
    BookInventoryReadModel bookInventoryReadModel;
    BookDemandReadModel bookDemandReadModel;

    public ProlongationPolicyDecider prolongationPolicyDecider() {
        return new ProlongationPolicyDecider(prolongationPolicyCalculators());
    }

    Collection<ProlongationPolicyCalculator> prolongationPolicyCalculators() {
        return Set.of(
                new ProlongationContextBasedCalculator(readerCatalog),
                new ReaderScoringBasedCalculator(readerCatalog, readerScoringReadModel),
                new BookDemandBasedCalculator(bookInventoryReadModel, bookDemandReadModel)
        );
    }
}
