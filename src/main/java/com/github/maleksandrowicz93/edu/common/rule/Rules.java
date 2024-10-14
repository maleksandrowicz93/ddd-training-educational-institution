package com.github.maleksandrowicz93.edu.common.rule;

import com.github.maleksandrowicz93.edu.common.result.Result;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.HashSet;

import static lombok.AccessLevel.PRIVATE;

public record Rules<T>(
        Collection<Rule<T>> all
) {

    public static <T> RulesBuilder<T> from() {
        return new RulesBuilder<>();
    }

    public Result examine(T t) {
        for (var rule : all) {
            var result = rule.apply(t);
            if (result.isFailed()) {
                return result;
            }
        }
        return Result.successful();
    }

    @FieldDefaults(level = PRIVATE, makeFinal = true)
    public static class RulesBuilder<T> {

        Collection<Rule<T>> rules = new HashSet<>();

        private RulesBuilder() {
        }

        public RulesBuilder<T> theRule(RuleFactory<T> ruleFactory) {
            rules.add(ruleFactory.get());
            return this;
        }

        public Rules<T> compose() {
            return new Rules<>(rules);
        }
    }
}
