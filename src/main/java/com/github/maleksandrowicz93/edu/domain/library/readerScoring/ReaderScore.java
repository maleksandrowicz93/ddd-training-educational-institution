package com.github.maleksandrowicz93.edu.domain.library.readerScoring;

import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

public record ReaderScore(
        ReaderId readerId,
        boolean hasScore,
        int value
) {

    public ReaderScore {
        if (!hasScore && value != 0) {
            throw new IllegalArgumentException("If does not have score, value should be 0");
        }
    }

    public static ReaderScore withValue(ReaderId readerId, int value) {
        return new ReaderScore(readerId, true, value);
    }

    public static ReaderScore empty(ReaderId readerId) {
        return new ReaderScore(readerId, false, 0);
    }

    public boolean isNegative() {
        return hasScore && value < 0;
    }

    public boolean isPositive() {
        return value >= 0;
    }

    public boolean isSuperior() {
        return hasScore && value > 0;
    }
}
