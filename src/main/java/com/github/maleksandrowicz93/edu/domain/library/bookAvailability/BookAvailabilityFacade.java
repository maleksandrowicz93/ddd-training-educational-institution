package com.github.maleksandrowicz93.edu.domain.library.bookAvailability;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.availability.OwnerId;
import com.github.maleksandrowicz93.edu.domain.availability.ResourceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookEditionId;
import com.github.maleksandrowicz93.edu.domain.library.shared.BookInstanceId;
import com.github.maleksandrowicz93.edu.domain.library.shared.ReaderId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookAvailabilityFacade {

    AvailabilityFacade availabilityFacade;

    public void createBookAvailabilityUnits(BookEditionId bookEditionId, Collection<BookInstanceId> bookInstanceIds) {
        var parentId = new ResourceId(bookEditionId.value());
        var resourceIds = bookInstanceIds.stream()
                                         .map(BookInstanceId::value)
                                         .map(ResourceId::new)
                                         .collect(toSet());
        availabilityFacade.createAvailabilityUnitsForParent(parentId, resourceIds);
    }

    public void createBookAvailabilityUnit(BookEditionId bookEditionId, BookInstanceId bookInstanceId) {
        createBookAvailabilityUnits(bookEditionId, Set.of(bookInstanceId));
    }

    public void deleteBookAvailabilityUnit(BookInstanceId bookInstanceId) {
        var resourceId = new ResourceId(bookInstanceId.value());
        availabilityFacade.deleteAvailabilityUnitByResourceId(resourceId);
    }

    public Optional<BookInstanceId> takeAnyBookOf(BookEditionId bookEditionId, ReaderId readerId) {
        var parentId = new ResourceId(bookEditionId.value());
        var ownerId = new OwnerId(readerId.value());
        return availabilityFacade.blockRandomOf(parentId, ownerId)
                                 .map(ResourceId::value)
                                 .map(BookInstanceId::new);
    }

    public void releaseBook(BookInstanceId bookInstanceId, ReaderId readerId) {
        var resourceId = new ResourceId(bookInstanceId.value());
        var ownerId = new OwnerId(readerId.value());
        availabilityFacade.release(resourceId, ownerId);
    }

    public void releaseBooks(Collection<BookInstanceId> bookIds, ReaderId readerId) {
        var resourceIds = bookIds.stream()
                                 .map(BookInstanceId::value)
                                 .map(ResourceId::new)
                                 .collect(toSet());
        var ownerId = new OwnerId(readerId.value());
        availabilityFacade.releaseAll(resourceIds, ownerId);
    }
}
