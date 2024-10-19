package com.github.maleksandrowicz93.edu.domain.availability;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collector;

public record GroupedAvailabilitySummary(
        ResourceId parentId,
        int unitsNumber,
        Collection<OwnerId> owners
) {

    static Collector<Optional<OwnerId>, HashSet<OwnerId>, GroupedAvailabilitySummary> from(
            ResourceId parentId,
            int unitsNumber
    ) {
        return Collector.of(
                HashSet::new,
                (owners, maybeOwner) -> maybeOwner.ifPresent(owners::add),
                (a, b) -> {
                    a.addAll(b);
                    return a;
                },
                owners -> new GroupedAvailabilitySummary(parentId, unitsNumber, owners)
        );
    }
}
