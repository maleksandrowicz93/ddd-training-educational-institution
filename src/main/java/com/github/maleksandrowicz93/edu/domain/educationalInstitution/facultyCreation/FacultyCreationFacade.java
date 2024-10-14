package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation;

import com.github.maleksandrowicz93.edu.common.capacity.Capacity;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalogEntry;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class FacultyCreationFacade {

    FacultyCreationConfig config;
    FacultyCatalog facultyCatalog;
    FacultyServices facultyServices;

    public Optional<FacultyId> createFaculty(FacultyCreationApplication application) {
        log.info("Creating faculty {}...", application);
        var facultyName = application.name();
        if (facultyCatalog.existsByName(facultyName)) {
            log.info("Faculty with name {} already exists", facultyName);
            return Optional.empty();
        }
        var facultyId = FacultyId.newOne();
        var specification = new FacultyServicesSpecification(
                facultyId,
                application.professorVacancies(),
                application.studentVacancies(),
                Capacity.of(config.maxCoursesNumber())
        );
        facultyServices.open(specification);
        var faculty = new FacultyCatalogEntry(
                facultyId,
                facultyName,
                application.mainFieldOfStudy(),
                application.secondaryFieldsOfStudies()
        );
        facultyCatalog.save(faculty);
        return Optional.of(faculty.id());
    }
}
