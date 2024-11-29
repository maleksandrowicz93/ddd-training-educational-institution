package com.github.maleksandrowicz93.edu.domain.library.prolongationPolicyDecider;

import com.github.maleksandrowicz93.edu.domain.library.bookDemand.BookDemandReadModel;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.BookInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.library.prolongPolicies.ProlongPolicies;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class BookDemandBasedCalculator implements ProlongationPolicyCalculator {

    BookInventoryReadModel bookInventoryReadModel;
    BookDemandReadModel bookDemandReadModel;

    @Override
    public ProlongPolicies calculatePoliciesFor(ReaderId readerId) {
        return ProlongPolicies.from()
                              .policy(context -> {
                                  var isbn = bookInventoryReadModel.getIsbnByBookInstanceId(context.bookInstanceId());
                                  return bookDemandReadModel.isDemanded(isbn);
                              })
                              .compose();
    }
}
