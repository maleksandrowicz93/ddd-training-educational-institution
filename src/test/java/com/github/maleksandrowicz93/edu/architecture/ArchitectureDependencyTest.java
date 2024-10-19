package com.github.maleksandrowicz93.edu.architecture;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


public class ArchitectureDependencyTest {

    private final JavaClasses classes = new ClassFileImporter().importPackages("com.github.maleksandrowicz93.edu");

    @Test
    void checkApplicationDependencies() {
        layeredArchitecture().consideringOnlyDependenciesInLayers()
                             .layer("application").definedBy("com.github.maleksandrowicz93.edu.application..")
                             .layer("common").definedBy("com.github.maleksandrowicz93.edu.common..")
                             .layer("domain").definedBy("com.github.maleksandrowicz93.edu.domain..")
                             .layer("query").definedBy("com.github.maleksandrowicz93.edu.query..")
                             .whereLayer("common").mayNotAccessAnyLayer()
                             .whereLayer("domain").mayOnlyAccessLayers("common")
                             .whereLayer("query").mayOnlyAccessLayers("common", "domain")
                             .whereLayer("application").mayNotBeAccessedByAnyLayer()
                             .check(classes);
    }

    @Test
    void checkEducationalInstitutionDependencies() {
        layeredArchitecture().consideringOnlyDependenciesInLayers()
                             .layer("courseCatalog").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog..")
                             .layer("courseCreation").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation..")
                             .layer("courseLeadership").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership..")
                             .layer("facultyCatalog").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog..")
                             .layer("facultyCreation").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation..")
                             .layer("inventory").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory..")
                             .layer("professorCatalog").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog..")
                             .layer("professorEmployment").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment..")
                             .layer("shared").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared..")
                             .layer("studentEnrollment").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment..")
                             .whereLayer("shared").mayNotAccessAnyLayer()
                             .whereLayer("courseCatalog").mayOnlyAccessLayers("shared")
                             .whereLayer("facultyCatalog").mayOnlyAccessLayers("shared")
                             .whereLayer("professorCatalog").mayOnlyAccessLayers("shared")
                             .whereLayer("inventory").mayOnlyAccessLayers("shared")
                             .whereLayer("facultyCreation").mayOnlyAccessLayers("shared", "inventory", "facultyCatalog")
                             .whereLayer("courseLeadership").mayOnlyAccessLayers("shared", "inventory", "courseCatalog", "professorCatalog")
                             .whereLayer("studentEnrollment").mayOnlyAccessLayers("shared", "inventory", "facultyCatalog")
                             .whereLayer("courseCreation").mayOnlyAccessLayers("shared", "inventory", "courseCatalog", "courseLeadership", "studentEnrollment")
                             .whereLayer("professorEmployment").mayOnlyAccessLayers("shared", "inventory", "facultyCatalog", "professorCatalog", "courseLeadership")
                             .check(classes);
    }

    @Test
    void checkSubdomainsDependencies() {
        layeredArchitecture().consideringOnlyDependenciesInLayers()
                             .layer("availability").definedBy("com.github.maleksandrowicz93.edu.domain.availability..")
                             .layer("courseCatalog").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog..")
                             .layer("courseCreation").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCreation..")
                             .layer("courseLeadership").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseLeadership..")
                             .layer("facultyCatalog").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog..")
                             .layer("facultyCreation").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCreation..")
                             .layer("inventory").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.inventory..")
                             .layer("professorCatalog").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog..")
                             .layer("professorEmployment").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorEmployment..")
                             .layer("shared").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared..")
                             .layer("studentEnrollment").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution.studentEnrollment..")
                             .whereLayer("availability").mayOnlyBeAccessedByLayers("courseLeadership", "inventory")
                             .check(classes);
    }
}
