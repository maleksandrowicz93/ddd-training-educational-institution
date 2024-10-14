package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
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
class ProfessorInventory {

    EducationalInstitutionInventoryFacade inventoryFacade;

    Optional<ProfessorId> addToFaculty(FacultyId facultyId) {
        var professorEmployment = EmployedProfessors.FACTORY.apply(facultyId);
        return inventoryFacade.addItemToInventoryOfType(professorEmployment, ProfessorId::new);
    }

    void openCourseInventoryFor(ProfessorId professorId, Capacity capacity) {
        var professorCourses = ProfessorCourses.FACTORY.apply(professorId);
        var inventoryTypeCreation = new InventoryTypeCreation(professorCourses, capacity);
        inventoryFacade.createInventoryOfType(inventoryTypeCreation);
    }

    void removeFromFaculty(ProfessorId professorId, FacultyId facultyId) {
        inventoryFacade.removeInventoryOfType(ProfessorCourses.FACTORY.apply(professorId));
        var professorEmployment = EmployedProfessors.FACTORY.apply(facultyId);
        inventoryFacade.removeItem(professorId, professorEmployment);
    }

    private enum ProfessorCourses implements Function<ProfessorId, InventoryType> {

        FACTORY;

        @Override
        public InventoryType apply(ProfessorId professorId) {
            return new InventoryType(
                    new Unit(professorId, PROFESSOR),
                    COURSE
            );
        }
    }
}
