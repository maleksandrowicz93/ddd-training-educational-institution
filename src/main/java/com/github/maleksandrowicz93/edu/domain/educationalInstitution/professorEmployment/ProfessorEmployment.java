package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.common.infra.Transactional;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryType;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.InventoryTypeCreation;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.Unit;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.function.Function;

import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.ItemType.COURSE;
import static com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.UnitType.PROFESSOR;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class ProfessorEmployment {

    EducationalInstitutionInventoryFacade inventoryFacade;

    @Transactional
    Optional<ProfessorId> employNewAt(FacultyId facultyId, Capacity professorCapacity) {
        var professorEmployment = EmployedProfessors.FACTORY.apply(facultyId);
        var maybeAdded = inventoryFacade.addItemToInventoryOfType(professorEmployment, ProfessorId::new);
        maybeAdded.ifPresent(professorId -> {
            var professorCourses = ProfessorCourses.FACTORY.apply(professorId);
            var inventoryTypeCreation = new InventoryTypeCreation(professorCourses, professorCapacity);
            inventoryFacade.createInventoryOfType(inventoryTypeCreation);
        });
        return maybeAdded;
    }

    @Transactional
    void resign(ProfessorId professorId, FacultyId facultyId) {
        inventoryFacade.removeInventoryOfType(ProfessorCourses.FACTORY.apply(professorId));
        var professorEmployment = EmployedProfessors.FACTORY.apply(facultyId);
        inventoryFacade.removeItem(professorId, professorEmployment);
    }

    private enum ProfessorCourses implements Function<ProfessorId, InventoryType> {

        FACTORY {
            @Override
            public InventoryType apply(ProfessorId professorId) {
                return new InventoryType(
                        new Unit(professorId, PROFESSOR),
                        COURSE
                );
            }
        }
    }
}
