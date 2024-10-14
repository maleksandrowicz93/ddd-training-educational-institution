package com.github.maleksandrowicz93.edu.scenarios;

import com.github.maleksandrowicz93.edu.application.ApplicationConfig;
import com.github.maleksandrowicz93.edu.application.ApplicationServicesFactory;
import com.github.maleksandrowicz93.edu.application.Injector;
import com.github.maleksandrowicz93.edu.common.infra.InMemoryAbstractRepo;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFactory;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryFacade;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryFactory;
import com.github.maleksandrowicz93.edu.domain.inventory.InventoryReadModel;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InjectorFactoryForTests {

    ApplicationConfig applicationConfig;
    AvailabilityFactory availabilityFactory;
    InventoryFactory inventoryFactory;
    EducationalInstitutionInventoryFactory educationalInstitutionInventoryFactory;

    public InjectorFactoryForTests(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        availabilityFactory = AvailabilityFactory.forTests();
        inventoryFactory = InventoryFactory.forTests();
        educationalInstitutionInventoryFactory = EducationalInstitutionInventoryFactory.forTests(
                inventoryReadModel(),
                inventoryFacade()
        );
    }

    public Injector createInjector() {
        return new ApplicationServicesFactory(
                applicationConfig,
                facultyCatalog(),
                professorCatalog(),
                courseCatalog(),
                availabilityFacade(),
                availabilityReadModel(),
                educationalInstitutionInventoryReadModel(),
                educationalInstitutionInventoryFacade()
        );
    }

    //catalogs

    CourseCatalog courseCatalog() {
        return new InMemoryCourseCatalog();
    }

    FacultyCatalog facultyCatalog() {
        return new InMemoryFacultyCatalog();
    }

    ProfessorCatalog professorCatalog() {
        return new InMemoryProfessorCatalog();
    }

    //read models

    AvailabilityReadModel availabilityReadModel() {
        return availabilityFactory.availabilityReadModel();
    }

    InventoryReadModel inventoryReadModel() {
        return inventoryFactory.inventoryReadModel();
    }

    EducationalInstitutionInventoryReadModel educationalInstitutionInventoryReadModel() {
        return educationalInstitutionInventoryFactory.educationalInstitutionInventoryReadModel();
    }

    //facades

    AvailabilityFacade availabilityFacade() {
        return availabilityFactory.availabilityFacade();
    }

    InventoryFacade inventoryFacade() {
        return inventoryFactory.inventoryFacade();
    }

    EducationalInstitutionInventoryFacade educationalInstitutionInventoryFacade() {
        return educationalInstitutionInventoryFactory.educationalInstitutionInventoryFacade();
    }

    //in memory catalogs implementations

    private static class InMemoryCourseCatalog extends InMemoryAbstractRepo<CourseId, CourseCatalogEntry>
            implements CourseCatalog {
    }

    private static class InMemoryFacultyCatalog extends InMemoryAbstractRepo<FacultyId, FacultyCatalogEntry>
            implements FacultyCatalog {

        @Override
        public boolean existsByName(String name) {
            return existsBy(faculty -> name.equals(faculty.name()));
        }
    }

    private static class InMemoryProfessorCatalog extends InMemoryAbstractRepo<ProfessorId, ProfessorCatalogEntry>
            implements ProfessorCatalog {
    }
}
