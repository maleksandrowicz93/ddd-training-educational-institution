package com.github.maleksandrowicz93.edu.domain.library.libraryCard;

import java.time.Duration;
import java.util.function.Predicate;

public interface ProlongPolicy extends Predicate<Duration> {
}
