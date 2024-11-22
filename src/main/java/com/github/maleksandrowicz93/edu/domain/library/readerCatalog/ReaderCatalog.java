package com.github.maleksandrowicz93.edu.domain.library.readerCatalog;

import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;

import java.util.Optional;

public interface ReaderCatalog {

    void save(Reader reader);

    void deleteById(ReaderId readerId);

    boolean existsById(ReaderId readerId);

    boolean existsByIdCardNumber(String idCardNumber);

    Optional<Reader> findById(ReaderId id);

    Optional<ReaderType> findReaderTypeById(ReaderId id);

    default Reader getById(ReaderId id) {
        return findById(id).orElseThrow();
    }

    default ReaderType getReaderTypeById(ReaderId id) {
        return findReaderTypeById(id).orElseThrow();
    }
}
