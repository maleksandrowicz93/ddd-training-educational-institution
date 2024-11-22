package com.github.maleksandrowicz93.edu.domain.availability;

import com.github.maleksandrowicz93.edu.common.infra.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Accessors(fluent = true)
@AllArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
class AvailabilityUnit implements Entity<AvailabilityUnitId> {

    @Getter
    final AvailabilityUnitId id;
    @Getter
    final ResourceId parentId;
    @Getter
    final ResourceId resourceId;
    Blockade blockade;
    @Getter
    int version = 0;

    private AvailabilityUnit(
            AvailabilityUnitId id,
            ResourceId parentId,
            ResourceId resourceId,
            Blockade blockade
    ) {
        this.id = id;
        this.parentId = parentId;
        this.resourceId = resourceId;
        this.blockade = blockade;
    }

    static AvailabilityUnit forParent(ResourceId parentId) {
        return new AvailabilityUnit(
                AvailabilityUnitId.newOne(),
                parentId,
                ResourceId.newOne(),
                Blockade.NONE
        );
    }

    static AvailabilityUnit forParent(ResourceId parentId, ResourceId resourceId) {
        return new AvailabilityUnit(
                AvailabilityUnitId.newOne(),
                parentId,
                resourceId,
                Blockade.NONE
        );
    }

    static AvailabilityUnit blocked(ResourceId resourceId, OwnerId ownerId) {
        return new AvailabilityUnit(
                AvailabilityUnitId.newOne(),
                null,
                resourceId,
                Blockade.by(ownerId)
        );
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

    Optional<OwnerId> owner() {
        return blockade.byNone()
                ? Optional.empty()
                : Optional.of(blockade.blockedBy());
    }

    void incrementVersion() {
        version++;
    }
}
