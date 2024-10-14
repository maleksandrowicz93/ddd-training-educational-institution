package com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public record FieldsOfStudies(
        Collection<FieldOfStudy> all
) {

    public static Collector<FieldOfStudy, Collection<FieldOfStudy>, FieldsOfStudies> toFieldsOfStudies() {
        return Collector.of(
                HashSet::new,
                Collection::add,
                (a, b) -> {
                    a.addAll(b);
                    return a;
                },
                FieldsOfStudies::new
        );
    }

    public int count() {
        return all.size();
    }

    public int numberOfMatched(FieldsOfStudies fieldsOfStudies) {
        return (int) all.stream()
                        .filter(fieldsOfStudies.all::contains)
                        .count();
    }

    public boolean matchesAllOf(FieldsOfStudies fieldsOfStudies) {
        return all.containsAll(fieldsOfStudies.all);
    }

    public FieldsOfStudies add(FieldOfStudy fieldOfStudyId) {
        return new FieldsOfStudies(
                Stream.concat(all.stream(), Stream.of(fieldOfStudyId))
                      .collect(toSet())
        );
    }
}
