package com.github.maleksandrowicz93.edu.domain.library.prolongPolicies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

public record ProlongPolicies(
        Collection<ProlongPolicy> all
) {

    public static ProlongPolicies EMPTY = new ProlongPolicies(Set.of());

    public static ProlongPoliciesBuilder from() {
        return new ProlongPoliciesBuilder();
    }

    public ProlongPolicies(Collection<ProlongPolicy> all) {
        this.all = Set.copyOf(all);
    }

    public boolean examine(ProlongationContext prolongationContext) {
        if (all.isEmpty()) {
            return true;
        }
        return all.stream()
                  .allMatch(policy -> policy.test(prolongationContext));
    }

    public static Collector<ProlongPolicy, Object, ProlongPolicies> toProlongPolicies() {
        return collectingAndThen(
                toSet(),
                ProlongPolicies::new
        );
    }

    public static Collector<ProlongPolicies, Object, ProlongPolicies> sumProlongationPolicies() {
        return collectingAndThen(
                toSet(),
                policiesSet -> policiesSet.stream()
                                          .map(ProlongPolicies::all)
                                          .flatMap(Collection::stream)
                                          .collect(toProlongPolicies())
        );
    }

    public static class ProlongPoliciesBuilder {

        private final Collection<ProlongPolicy> policies = new HashSet<>();

        public ProlongPoliciesBuilder policy(ProlongPolicy prolongPolicy) {
            policies.add(prolongPolicy);
            return this;
        }

        public ProlongPolicies compose() {
            return new ProlongPolicies(policies);
        }
    }
}
