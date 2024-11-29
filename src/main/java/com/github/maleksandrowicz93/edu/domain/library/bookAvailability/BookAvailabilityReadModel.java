package com.github.maleksandrowicz93.edu.domain.library.bookAvailability;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.availability.ResourceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookEditionId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;

import java.util.Optional;

public class BookAvailabilityReadModel {

    AvailabilityReadModel availabilityReadModel;

    public boolean isBookAvailable(BookInstanceId bookInstanceId) {
        var resourceId = new ResourceId(bookInstanceId.value());
        return availabilityReadModel.isResourceAvailable(resourceId);
    }

    public Optional<BookEditionId> findBookEditionIdByBookInstanceId(BookInstanceId bookInstanceId) {
        var resourceId = new ResourceId(bookInstanceId.value());
        return availabilityReadModel.findParentIdByResourceId(resourceId)
                                    .map(ResourceId::value)
                                    .map(BookEditionId::new);
    }
}
