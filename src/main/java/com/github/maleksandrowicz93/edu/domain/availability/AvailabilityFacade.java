package com.github.maleksandrowicz93.edu.domain.availability;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
        availabilityRepo.save(availabilityUnit);
    }

    public void deleteAvailabilityUnitByResourceId(ResourceId resourceId) {
        log.info("Deleting availability unit for resource {}", resourceId);
        availabilityRepo.deleteByResourceId(resourceId);
    }

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
