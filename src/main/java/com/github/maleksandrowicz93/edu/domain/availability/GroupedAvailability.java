package com.github.maleksandrowicz93.edu.domain.availability;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
class GroupedAvailability {

    Collection<AvailabilityUnit> units;

    static GroupedAvailability of(ResourceId parentId, int unitsNumber) {
        return IntStream.rangeClosed(1, unitsNumber)
                        .mapToObj(_ -> AvailabilityUnit.forParent(parentId))
                        .collect(collectingAndThen(
                                toSet(),
                                GroupedAvailability::new
                        ));
    }

    Collection<AvailabilityUnit> units() {
        return Set.copyOf(units);
    }
}
