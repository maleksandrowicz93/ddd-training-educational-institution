package com.github.maleksandrowicz93.edu.domain.library.readerRegistration;

import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LibraryCardCreation;
import com.github.maleksandrowicz93.edu.domain.library.libraryCard.LibraryCardId;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.Reader;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderCatalog;
import com.github.maleksandrowicz93.edu.domain.library.readerScoring.ReaderScoring;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ReaderRegistration {

    ReaderCatalog readerCatalog;
    ReaderScoring readerScoring;
    LibraryCardCreation libraryCardCreation;

    public Optional<LibraryCardId> registerNewOne(ReaderRegistrationRequest request) {
        if (readerCatalog.existsByIdCardNumber(request.idCardNumber())) {
            return Optional.empty();
        }
        var reader = Reader.builder()
                           .id(ReaderId.newOne())
                           .idCardNumber(request.idCardNumber())
                           .fullName(request.fullName())
                           .readerType(request.readerType())
                           .build();
        var maybeLibraryCardId = libraryCardCreation.createLibraryCart(reader.id());
        maybeLibraryCardId.ifPresent(_ -> {
            readerCatalog.save(reader);
            readerScoring.initScoringFor(reader.id());
        });
        return maybeLibraryCardId;
    }

    public boolean unregisterReader(ReaderId readerId) {
        if (readerCatalog.existsById(readerId)) {
            var libraryCardDeleted = libraryCardCreation.deleteLibraryCard(readerId);
            if (libraryCardDeleted) {
                readerCatalog.deleteById(readerId);
                readerScoring.disableScoringFor(readerId);
                return true;
            }
        }
        return false;
    }
}
