package com.github.maleksandrowicz93.edu.common.rule;

import java.util.function.Supplier;

public interface RuleFactory<T> extends Supplier<Rule<T>> {
}
