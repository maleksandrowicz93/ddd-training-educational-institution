package com.github.maleksandrowicz93.edu.domain.library.prolongPolicies;

import java.util.function.Predicate;

public interface ProlongPolicy extends Predicate<ProlongationContext> {

    static ProlongPolicy not(ProlongPolicy prolongPolicy) {
        return prolongPolicy.negate()::test;
    }
}
