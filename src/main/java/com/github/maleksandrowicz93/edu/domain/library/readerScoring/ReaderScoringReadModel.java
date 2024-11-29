package com.github.maleksandrowicz93.edu.domain.library.readerScoring;

import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

public interface ReaderScoringReadModel {

    ReaderScore scoreOf(ReaderId readerId);
}
