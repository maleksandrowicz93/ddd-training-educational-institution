package com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared;

import java.util.Comparator;

public record PercentageScore(
        int value
) {

    public static final PercentageScore ZERO = new PercentageScore(0);

    public PercentageScore(int score, int max) {
        this(score / max);
    }

    public static Comparator<PercentageScore> comparator() {
        return Comparator.comparingInt(PercentageScore::value);
    }

    public boolean lessThan(PercentageScore percentageScore) {
        return value < percentageScore.value;
    }

    public boolean equalToOrGreaterThan(PercentageScore percentageScore) {
        return !lessThan(percentageScore);
    }
}
