package com.github.maleksandrowicz93.edu.domain.availability;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

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
        availabilityRepo.save(availabilityUnit);
    }

    public void createAvailabilityUnitsForParent(ResourceId parentId, int unitsNumber) {
        log.info("Creating {} availability units for parent {}...", unitsNumber, parentId);
        availabilityRepo.saveAll(
                IntStream.rangeClosed(1, unitsNumber)
                         .mapToObj(_ -> AvailabilityUnit.forParent(parentId, ResourceId.newOne()))
                         .toList()
        );
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

    public boolean blockRandom(Collection<ResourceId> resources, OwnerId ownerId) {
        log.info("Blocking random resource from {} by owner {}...", resources, ownerId);
        var resourcesToBlock = new HashSet<>(resources);
        while (!resourcesToBlock.isEmpty()) {
            var randomUnit = getRandomUnit(resourcesToBlock);
            var blocked = randomUnit.block(ownerId);
            if (blocked) {
                availabilityRepo.save(randomUnit);
                return true;
            }
            resourcesToBlock.remove(randomUnit.resourceId());
        }
        log.info("Owner {} cannot take any of resources {}", ownerId, resources);
        return false;
    }

    private AvailabilityUnit getRandomUnit(Set<ResourceId> resources) {
        var availabilityUnits = new ArrayList<>(availabilityRepo.findAllByIds(resources));
        Collections.shuffle(availabilityUnits);
        return availabilityUnits.getFirst();
    }

    @Transactional
    public boolean block(ResourceId resourceId, OwnerId ownerId) {
        log.info("Blocking resource {} by owner {}...", resourceId, ownerId);
        var blockingResult = new AtomicBoolean(false);
        availabilityRepo.findByResourceId(resourceId)
                        .ifPresentOrElse(
                                availabilityUnit -> {
                                    var blocked = availabilityUnit.block(ownerId);
                                    blockingResult.set(blocked);
                                    if (blocked) {
                                        availabilityRepo.save(availabilityUnit);
                                    } else {
                                        log.info("Resource {} was not blocked by owner{}", resourceId, ownerId);
                                    }
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
                                    releasingResult.set(released);
                                    if (released) {
                                        availabilityRepo.save(availabilityUnit);
                                    } else {
                                        log.info("Resource {} was not released by owner{}", resourceId, ownerId);
                                    }
                                },
                                () -> log.info(
                                        "Resource {} cannot be released by owner {} because availability unit not found",
                                        resourceId, ownerId
                                )
                        );
        return releasingResult.get();
    }
}
