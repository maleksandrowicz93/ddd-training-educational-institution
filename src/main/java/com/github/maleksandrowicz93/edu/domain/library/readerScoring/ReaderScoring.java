package com.github.maleksandrowicz93.edu.domain.library.readerScoring;

import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

public interface ReaderScoring {

    void initScoringFor(ReaderId readerId);

    void disableScoringFor(ReaderId readerId);

    void addPoints(ReaderId readerId, int points);

    void addPoint(ReaderId readerId);

    void subtractPoint(ReaderId readerId);
}
