package com.github.maleksandrowicz93.edu.domain.availability;

import com.github.maleksandrowicz93.edu.common.infra.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Accessors(fluent = true)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class AvailabilityUnit implements Entity<AvailabilityUnitId> {

    @Getter
    final AvailabilityUnitId id;
    final ResourceId parentId;
    final ResourceId resourceId;
    Blockade blockade;

    private AvailabilityUnit(ResourceId resourceId, Blockade blockade) {
        this(AvailabilityUnitId.newOne(), null, resourceId, blockade);
    }

    private AvailabilityUnit(ResourceId parentId, ResourceId resourceId, Blockade blockade) {
        this(AvailabilityUnitId.newOne(), parentId, resourceId, blockade);
    }

    static AvailabilityUnit forParent(ResourceId parentId, ResourceId resourceId) {
        return new AvailabilityUnit(parentId, resourceId, Blockade.NONE);
    }

    static AvailabilityUnit blocked(ResourceId resourceId, OwnerId ownerId) {
        return new AvailabilityUnit(resourceId, Blockade.by(ownerId));
    }

    boolean block(OwnerId ownerId) {
        if (blockade.blockedBy(ownerId)) {
            return true;
        }
        if (!blockade.byNone()) {
            return false;
        }
        blockade = Blockade.by(ownerId);
        return true;
    }

    boolean release(OwnerId ownerId) {
        if (blockade.byNone()) {
            return true;
        }
        if (!blockade.blockedBy(ownerId)) {
            return false;
        }
        blockade = Blockade.NONE;
        return true;
    }

    boolean isFree() {
        return blockade.byNone();
    }

    boolean isBlocked() {
        return !isFree();
    }

    boolean isBlockedBy(OwnerId ownerId) {
        return blockade.blockedBy(ownerId);
    }

    ResourceId parentId() {
        return parentId;
    }

    ResourceId resourceId() {
        return resourceId;
    }

    Optional<OwnerId> owner() {
        return blockade.byNone()
                ? Optional.empty()
                : Optional.of(blockade.blockedBy());
    }
}
