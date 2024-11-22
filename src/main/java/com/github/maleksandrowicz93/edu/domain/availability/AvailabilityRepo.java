package com.github.maleksandrowicz93.edu.domain.availability;

import com.github.maleksandrowicz93.edu.common.infra.OptimisticLockingException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface AvailabilityRepo {

    void saveNew(AvailabilityUnit availabilityUnit);

    void saveNew(GroupedAvailability groupedAvailability);

    void saveCheckingVersion(AvailabilityUnit availabilityUnit) throws OptimisticLockingException;

    void deleteByResourceId(ResourceId resourceId);

    void deleteAllByResourceIds(Collection<ResourceId> resourceIds);

    void deleteAllByParentId(ResourceId parentId);

    Optional<AvailabilityUnit> findByResourceId(ResourceId resourceId);

    List<AvailabilityUnit> findAllByParentId(ResourceId parentId);

    List<AvailabilityUnit> findAllByParentIds(Collection<ResourceId> parentIds);
}
