package com.github.maleksandrowicz93.edu.application;

import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityFacade;
import com.github.maleksandrowicz93.edu.domain.availability.AvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog.CourseCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation.CourseCreationReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseAvailabilityReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership.CourseLeadershipReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog.FacultyCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation.FacultyCreationFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory.EducationalInstitutionInventoryReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog.ProfessorCatalog;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment.ProfessorEmploymentReadModel;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentFacade;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentFactory;
import com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment.StudentEnrollmentReadModel;
import com.github.maleksandrowicz93.edu.query.course.CourseQueryFacade;
import com.github.maleksandrowicz93.edu.query.faculty.FacultyQueryFacade;
import com.github.maleksandrowicz93.edu.query.professor.ProfessorQueryFacade;

public record ApplicationServicesFactory(
        ApplicationConfig applicationConfig,
        FacultyCatalog facultyCatalog,
        ProfessorCatalog professorCatalog,
        CourseCatalog courseCatalog,
        AvailabilityFacade availabilityFacade,
        AvailabilityReadModel availabilityReadModel,
        EducationalInstitutionInventoryReadModel educationalInstitutionInventoryReadModel,
        EducationalInstitutionInventoryFacade educationalInstitutionInventoryFacade
) implements Injector {

    //query

    @Override
    public FacultyQueryFacade facultyQueryFacade() {
        return new FacultyQueryFacade(
                facultyCatalog,
                professorEmploymentReadModel(),
                studentEnrollmentReadModel(),
                courseCreationReadModel()
        );
    }

    @Override
    public ProfessorQueryFacade professorQueryFacade() {
        return new ProfessorQueryFacade(
                professorCatalog,
                courseLeadershipReadModel()
        );
    }

    @Override
    public CourseQueryFacade courseQueryFacade() {
        return new CourseQueryFacade(
                courseCatalog,
                professorCatalog,
                studentEnrollmentReadModel(),
                courseAvailabilityReadModel()
        );
    }

    //facades

    @Override
    public FacultyCreationFacade facultyCreationFacade() {
        return facultyCreationFactory().facultyCreationFacade();
    }

    @Override
    public StudentEnrollmentFacade studentEnrollmentFacade() {
        return studentEnrollmentFactory().studentEnrollmentFacade();
    }

    @Override
    public CourseLeadershipFacade courseLeadershipFacade() {
        return courseLeadershipFactory().courseLeadershipFacade();
    }

    @Override
    public ProfessorEmploymentFacade professorEmploymentFacade() {
        return professorEmploymentFactory().professorEmploymentFacade();
    }

    @Override
    public CourseCreationFacade courseCreationFacade() {
        return courseCreationFactory().courseCreationFacade();
    }

    //read models

    public StudentEnrollmentReadModel studentEnrollmentReadModel() {
        return studentEnrollmentFactory().studentEnrollmentReadModel();
    }

    public ProfessorEmploymentReadModel professorEmploymentReadModel() {
        return professorEmploymentFactory().professorEmploymentReadModel();
    }

    public CourseCreationReadModel courseCreationReadModel() {
        return courseCreationFactory().courseCreationReadModel();
    }

    public CourseLeadershipReadModel courseLeadershipReadModel() {
        return courseLeadershipFactory().courseLeadershipReadModel();
    }

    public CourseAvailabilityReadModel courseAvailabilityReadModel() {
        return courseLeadershipFactory().courseAvailabilityReadModel();
    }

    //factories

    FacultyCreationFactory facultyCreationFactory() {
        return new FacultyCreationFactory(
                applicationConfig.facultyCreationConfig(),
                facultyCatalog,
                educationalInstitutionInventoryFacade
        );
    }

    StudentEnrollmentFactory studentEnrollmentFactory() {
        return new StudentEnrollmentFactory(
                applicationConfig.studentEnrollmentAtFacultyConfig(),
                facultyCatalog,
                educationalInstitutionInventoryReadModel,
                educationalInstitutionInventoryFacade
        );
    }

    CourseLeadershipFactory courseLeadershipFactory() {
        return new CourseLeadershipFactory(
                professorCatalog,
                courseCatalog,
                availabilityReadModel,
                availabilityFacade,
                educationalInstitutionInventoryReadModel,
                educationalInstitutionInventoryFacade
        );
    }

    ProfessorEmploymentFactory professorEmploymentFactory() {
        return new ProfessorEmploymentFactory(
                applicationConfig.professorEmploymentConfig(),
                facultyCatalog,
                professorCatalog,
                educationalInstitutionInventoryReadModel,
                educationalInstitutionInventoryFacade,
                courseLeadershipFacade()
        );
    }

    CourseCreationFactory courseCreationFactory() {
        return new CourseCreationFactory(
                applicationConfig.courseCreationConfig(),
                applicationConfig.courseClosingConfig(),
                courseCatalog,
                educationalInstitutionInventoryReadModel,
                educationalInstitutionInventoryFacade,
                courseLeadershipFacade(),
                studentEnrollmentReadModel()
        );
    }
}
