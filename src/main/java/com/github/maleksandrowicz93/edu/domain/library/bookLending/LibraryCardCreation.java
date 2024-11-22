package com.github.maleksandrowicz93.edu.domain.library.bookLending;

import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LibraryCardCreation {

    LendingReadModel lendingReadModel;
    LendingRepo lendingRepo;
    LibraryCardRepo libraryCardRepo;

    public Optional<LibraryCardId> createLibraryCart(ReaderId readerId) {
        if (libraryCardRepo.existsByReaderId(readerId)) {
            return Optional.empty();
        }
        var libraryCard = LibraryCard.newOne(readerId);
        libraryCardRepo.saveNew(libraryCard);
        return Optional.of(libraryCard.id());
    }

    @Transactional
    public boolean deleteLibraryCard(ReaderId readerId) {
        var lendingIds = lendingReadModel.findActiveLendingIdsOf(readerId);
        if (lendingIds.isEmpty()) {
            libraryCardRepo.deleteByReaderId(readerId);
            lendingRepo.deleteAllByIds(lendingIds);
            return true;
        }
        return false;
    }
}
