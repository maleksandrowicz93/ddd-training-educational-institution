package com.github.maleksandrowicz93.edu.domain.availability;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface AvailabilityRepo {

    void save(AvailabilityUnit availabilityUnit);

    void saveAll(Collection<AvailabilityUnit> all);

    void deleteByResourceId(ResourceId resourceId);

    void deleteAllByResourceIds(Collection<ResourceId> resourceIds);

    void deleteAllByParentId(ResourceId parentId);

    Optional<AvailabilityUnit> findByResourceId(ResourceId resourceId);

    List<AvailabilityUnit> findAllByIds(Collection<ResourceId> resourceIds);

    List<AvailabilityUnit> findAllByParentId(ResourceId parentId);

    List<AvailabilityUnit> findAllByParentIds(Collection<ResourceId> parentIds);
}
