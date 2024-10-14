package com.github.maleksandrowicz93.edu.common.rule;

import com.github.maleksandrowicz93.edu.common.result.Result;

import java.util.function.Function;
import java.util.function.Predicate;

public record Rule<T>(
        Predicate<T> predicate,
        Function<T, String> toFailureReason
) implements Function<T, Result> {

    @Override
    public Result apply(T t) {
        return predicate.test(t)
                ? Result.successful()
                : Result.failed(toFailureReason.apply(t));
    }
}
