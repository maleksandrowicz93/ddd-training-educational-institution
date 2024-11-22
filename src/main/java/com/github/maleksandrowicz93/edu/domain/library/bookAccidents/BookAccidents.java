package com.github.maleksandrowicz93.edu.domain.library.bookAccidents;

import com.github.maleksandrowicz93.edu.domain.library.bookAvailability.BookAvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.library.bookInventory.BookInventory;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderCatalog;
import com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderType;
import com.github.maleksandrowicz93.edu.domain.library.readerScoring.ReaderScoring;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.EnumSet;

import static com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderType.FUNDER;
import static com.github.maleksandrowicz93.edu.domain.library.readerCatalog.ReaderType.STUDENT;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookAccidents {

    private static final Collection<ReaderType> PUNISHABLE = EnumSet.of(STUDENT, FUNDER);

    BookAvailabilityReadModel bookAvailabilityReadModel;
    ReaderCatalog readerCatalog;
    BookInventory bookInventory;
    ReaderScoring readerScoring;
    BookDamageRegistry bookDamageRegistry;

    public boolean removeBookFromLibrary(BookInstanceId bookInstanceId) {
        if (bookAvailabilityReadModel.isBookAvailable(bookInstanceId)) {
            bookInventory.removeBookInstance(bookInstanceId);
            return true;
        }
        return false;
    }

    public void registerBookDamage(BookInstanceId bookInstanceId, ReaderId perpetrator) {
        bookInventory.removeBookInstance(bookInstanceId);
        bookDamageRegistry.registerNewOne(BookDamage.of(bookInstanceId));
        var readerType = readerCatalog.getReaderTypeById(perpetrator);
        if (PUNISHABLE.contains(readerType)) {
            readerScoring.subtractPoint(perpetrator);
        }
    }
}
