package com.github.maleksandrowicz93.edu.domain.availability;

import com.github.maleksandrowicz93.edu.common.infra.InMemoryAbstractRepo;

import java.util.Collection;
import java.util.List;
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
    public void deleteAllByResourceIds(Collection<ResourceId> resourceIds) {
        resourceIds.forEach(this::deleteByResourceId);
    }

    @Override
    public void deleteAllByParentId(ResourceId parentId) {
        deleteBy(availabilityUnit -> parentId.equals(availabilityUnit.parentId()));
    }

    @Override
    public Optional<AvailabilityUnit> findByResourceId(ResourceId resourceId) {
        return findBy(PREDICATE_FACTORY.apply(resourceId));
    }

    @Override
    public List<AvailabilityUnit> findAllByIds(Collection<ResourceId> resourceIds) {
        return findAllBy(availabilityUnit -> resourceIds.contains(availabilityUnit.resourceId()));
    }

    @Override
    public List<AvailabilityUnit> findAllByParentId(ResourceId parentId) {
        return findAllBy(availabilityUnit -> parentId.equals(availabilityUnit.parentId()));
    }

    @Override
    public List<AvailabilityUnit> findAllByParentIds(Collection<ResourceId> parentIds) {
        return findAllBy(availabilityUnit -> parentIds.contains(availabilityUnit.parentId()));
    }
}
