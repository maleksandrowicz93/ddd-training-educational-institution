package com.github.maleksandrowicz93.edu.domain.availability;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AvailabilityFactory {

    AvailabilityRepo availabilityRepo;

    public static AvailabilityFactory forTests() {
        return new AvailabilityFactory(new InMemoryAvailabilityRepo());
    }

    public AvailabilityReadModel availabilityReadModel() {
        return new AvailabilityReadModel(availabilityRepo);
    }

    public AvailabilityFacade availabilityFacade() {
        return new AvailabilityFacade(availabilityRepo);
    }
}
