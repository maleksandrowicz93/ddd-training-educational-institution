package com.github.maleksandrowicz93.edu.common.composer;

import java.util.function.BiFunction;

public interface Composer<R, C> extends BiFunction<R, C, R> {
}
