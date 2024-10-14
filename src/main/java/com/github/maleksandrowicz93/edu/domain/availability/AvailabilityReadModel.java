package com.github.maleksandrowicz93.edu.domain.availability;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AvailabilityReadModel {

    AvailabilityRepo availabilityRepo;

    public Optional<OwnerId> findResourceOwner(ResourceId resourceId) {
        return availabilityRepo.findByResourceId(resourceId)
                               .flatMap(AvailabilityUnit::owner);
    }
}
