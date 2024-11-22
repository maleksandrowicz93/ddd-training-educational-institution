package com.github.maleksandrowicz93.edu.domain.availability;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;

import static com.github.maleksandrowicz93.edu.domain.availability.GroupedAvailabilitySummary.toGroupedAvailabilitySummary;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AvailabilityReadModel {

    AvailabilityRepo availabilityRepo;

    public boolean isResourceAvailable(ResourceId resourceId) {
        return availabilityRepo.findByResourceId(resourceId)
                               .map(AvailabilityUnit::isFree)
                               .orElse(false);
    }

    public Optional<OwnerId> findResourceOwner(ResourceId resourceId) {
        return availabilityRepo.findByResourceId(resourceId)
                               .flatMap(AvailabilityUnit::owner);
    }

    public Collection<ResourceId> filterParentsByResourcesBlockedBy(OwnerId ownerId, Collection<ResourceId> parentIds) {
        return availabilityRepo.findAllByParentIds(parentIds)
                               .stream()
                               .filter(availabilityUnit -> availabilityUnit.isBlockedBy(ownerId))
                               .map(AvailabilityUnit::parentId)
                               .collect(toSet());
    }

    public Optional<ResourceId> findAnyBlockedResourceBy(OwnerId ownerId, ResourceId parentId) {
        return availabilityRepo.findAllByParentId(parentId)
                               .stream()
                               .filter(availabilityUnit -> availabilityUnit.isBlockedBy(ownerId))
                               .map(AvailabilityUnit::resourceId)
                               .findFirst();
    }

    public GroupedAvailabilitySummary getParentSummary(ResourceId parentId) {
        var all = availabilityRepo.findAllByParentId(parentId);
        return all.stream()
                  .filter(AvailabilityUnit::isBlocked)
                  .map(AvailabilityUnit::owner)
                  .flatMap(Optional::stream)
                  .collect(toGroupedAvailabilitySummary(parentId, all.size()));
    }

    public int countAllBlockedResourcesFor(ResourceId parentId) {
        return (int) availabilityRepo.findAllByParentId(parentId)
                                     .stream()
                                     .filter(AvailabilityUnit::isBlocked)
                                     .count();
    }

    public int countAllFor(ResourceId parentId) {
        return availabilityRepo.findAllByParentId(parentId).size();
    }
}
