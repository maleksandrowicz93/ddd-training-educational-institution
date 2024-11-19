package com.github.maleksandrowicz93.edu.domain.availability;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AvailabilityFacade {

    AvailabilityRepo availabilityRepo;

    public void createBlockedAvailabilityUnitFor(ResourceId resourceId, OwnerId ownerId) {
        log.info("Creating availability unit for resource {} blocked by {}...", resourceId, ownerId);
        var availabilityUnit = AvailabilityUnit.blocked(resourceId, ownerId);
        availabilityRepo.saveNew(availabilityUnit);
    }

    public void createAvailabilityUnitsForParent(ResourceId parentId, int unitsNumber) {
        log.info("Creating {} availability units for parent {}...", unitsNumber, parentId);
        availabilityRepo.saveNew(GroupedAvailability.of(parentId, unitsNumber));
    }

    public void deleteAvailabilityUnitByResourceId(ResourceId resourceId) {
        log.info("Deleting availability unit for resource {}...", resourceId);
        availabilityRepo.deleteByResourceId(resourceId);
    }

    public void deleteAllAvailabilityUnitsForParent(ResourceId parentId) {
        log.info("Deleting all availability units for parent {}...", parentId);
        availabilityRepo.deleteAllByParentId(parentId);
    }

    public void deleteRandomAvailabilityUnitsForParent(ResourceId parentId, int howManyUnitsDelete) {
        log.info("Deleting {} random free availability units for resource {}...", howManyUnitsDelete, parentId);
        var unitsToBeDeleted = availabilityRepo.findAllByParentId(parentId)
                                               .stream()
                                               .filter(AvailabilityUnit::isFree)
                                               .limit(howManyUnitsDelete)
                                               .map(AvailabilityUnit::resourceId)
                                               .toList();
        availabilityRepo.deleteAllByResourceIds(unitsToBeDeleted);
    }

    public Optional<ResourceId> blockRandomOf(ResourceId parentId, OwnerId ownerId) {
        log.info("Blocking random resource of parent {} by owner {}...", parentId, ownerId);
        var resourcesToBlock = new HashSet<>(findRandomFreeUnitOf(parentId));
        while (!resourcesToBlock.isEmpty()) {
            var randomResource = getRandomResource(resourcesToBlock);
            var blocked = randomResource.block(ownerId);
            if (blocked) {
                var updated = availabilityRepo.saveCheckingVersion(randomResource);
                if (updated) {
                    return Optional.of(randomResource.resourceId());
                }
            }
            resourcesToBlock.remove(randomResource);
        }
        log.info("Owner {} cannot take any of parent {} resources", ownerId, parentId);
        return Optional.empty();
    }

    private Collection<AvailabilityUnit> findRandomFreeUnitOf(ResourceId parentId) {
        return availabilityRepo.findAllByParentId(parentId)
                               .stream()
                               .filter(AvailabilityUnit::isFree)
                               .toList();
    }

    private AvailabilityUnit getRandomResource(Collection<AvailabilityUnit> availabilityUnits) {
        var unitList = new ArrayList<>(availabilityUnits);
        Collections.shuffle(unitList);
        return unitList.getFirst();
    }

    @Transactional
    public boolean block(ResourceId resourceId, OwnerId ownerId) {
        log.info("Blocking resource {} by owner {}...", resourceId, ownerId);
        var blockingResult = new AtomicBoolean(false);
        availabilityRepo.findByResourceId(resourceId)
                        .ifPresentOrElse(
                                availabilityUnit -> {
                                    var blocked = availabilityUnit.block(ownerId);
                                    if (blocked) {
                                        var updated = availabilityRepo.saveCheckingVersion(availabilityUnit);
                                        if (updated) {
                                            blockingResult.set(true);
                                            return;
                                        }
                                    }
                                    log.info("Resource {} was not blocked by owner{}", resourceId, ownerId);
                                },
                                () -> log.info(
                                        "Resource {} cannot be blocked by owner {} because availability unit not found",
                                        resourceId, ownerId
                                )
                        );
        return blockingResult.get();
    }

    @Transactional
    public boolean release(ResourceId resourceId, OwnerId ownerId) {
        log.info("Releasing resource {} by owner {}...", resourceId, ownerId);
        var releasingResult = new AtomicBoolean(false);
        availabilityRepo.findByResourceId(resourceId)
                        .ifPresentOrElse(
                                availabilityUnit -> {
                                    var released = availabilityUnit.release(ownerId);
                                    if (released) {
                                        var updated = availabilityRepo.saveCheckingVersion(availabilityUnit);
                                        if (updated) {
                                            releasingResult.set(true);
                                            return;
                                        }
                                    }
                                    log.info("Resource {} was not released by owner{}", resourceId, ownerId);
                                },
                                () -> log.info(
                                        "Resource {} cannot be released by owner {} because availability unit not found",
                                        resourceId, ownerId
                                )
                        );
        return releasingResult.get();
    }
}
