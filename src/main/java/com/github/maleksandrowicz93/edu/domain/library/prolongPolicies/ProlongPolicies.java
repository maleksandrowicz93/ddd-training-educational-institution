package com.github.maleksandrowicz93.edu.domain.library.prolongPolicies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
