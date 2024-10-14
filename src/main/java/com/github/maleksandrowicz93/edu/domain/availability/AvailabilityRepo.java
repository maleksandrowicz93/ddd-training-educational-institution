package com.github.maleksandrowicz93.edu.domain.availability;

import java.util.Optional;

interface AvailabilityRepo {

    void save(AvailabilityUnit availabilityUnit);

    void deleteByResourceId(ResourceId resourceId);

    Optional<AvailabilityUnit> findByResourceId(ResourceId resourceId);
}
