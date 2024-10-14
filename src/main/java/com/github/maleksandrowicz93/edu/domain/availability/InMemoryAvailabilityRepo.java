package com.github.maleksandrowicz93.edu.domain.availability;

import com.github.maleksandrowicz93.edu.common.infra.InMemoryAbstractRepo;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class InMemoryAvailabilityRepo extends InMemoryAbstractRepo<AvailabilityUnitId, AvailabilityUnit> implements AvailabilityRepo {

    private static final Function<ResourceId, Predicate<AvailabilityUnit>> PREDICATE_FACTORY =
            resourceId -> availabilityUnit -> resourceId.equals(availabilityUnit.resourceId());

    @Override
    public void deleteByResourceId(ResourceId resourceId) {
        deleteBy(PREDICATE_FACTORY.apply(resourceId));
    }

    @Override
    public Optional<AvailabilityUnit> findByResourceId(ResourceId resourceId) {
        return findBy(PREDICATE_FACTORY.apply(resourceId));
    }
}
