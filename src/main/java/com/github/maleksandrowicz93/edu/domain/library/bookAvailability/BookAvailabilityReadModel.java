package com.github.maleksandrowicz93.edu.domain.library.bookAvailability;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.availability.ResourceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;

public class BookAvailabilityReadModel {

    AvailabilityReadModel availabilityReadModel;

    public boolean isBookAvailable(BookInstanceId bookInstanceId) {
        var resourceId = new ResourceId(bookInstanceId.value());
        return availabilityReadModel.isResourceAvailable(resourceId);
    }
}
