package com.github.maleksandrowicz93.edu.domain.availability;

import java.util.Collection;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public record GroupedAvailabilitySummary(
        ResourceId parentId,
        int unitsNumber,
        Collection<OwnerId> owners
) {

    static Collector<OwnerId, Object, GroupedAvailabilitySummary> toGroupedAvailabilitySummary(
            ResourceId parentId,
            int unitsNumber
    ) {
        return collectingAndThen(
                toSet(),
                owners -> new GroupedAvailabilitySummary(parentId, unitsNumber, owners)
        );
    }
}
