package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import java.time.Duration;
import java.util.Collection;
import java.util.Set;

public record ProlongPolicies(
        Collection<ProlongPolicy> all
) {

    public static ProlongPolicies EMPTY = new ProlongPolicies(Set.of());

    public ProlongPolicies(Collection<ProlongPolicy> all) {
        this.all = Set.copyOf(all);
    }

    public boolean examine(Duration duration) {
        if (all.isEmpty()) {
            return true;
        }
        return all.stream()
                  .allMatch(policy -> policy.test(duration));
    }
}
