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
    void checkDomainDependencies() {
        layeredArchitecture().consideringOnlyDependenciesInLayers()
                             .layer("availability").definedBy("com.github.maleksandrowicz93.edu.domain.availability..")
                             .layer("educationalInstitution").definedBy("com.github.maleksandrowicz93.edu.domain.educationalInstitution..")
                             .layer("inventory").definedBy("com.github.maleksandrowicz93.edu.domain.inventory..")
                             .whereLayer("availability").mayNotAccessAnyLayer()
                             .whereLayer("inventory").mayNotAccessAnyLayer()
                             .whereLayer("educationalInstitution").mayOnlyAccessLayers("availability", "inventory")
                             .check(classes);
    }
}
