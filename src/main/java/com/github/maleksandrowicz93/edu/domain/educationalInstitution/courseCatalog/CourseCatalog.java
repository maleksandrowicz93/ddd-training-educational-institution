package com.github.maleksandrowicz93.edu.domain.educationalInstitution.courseCatalog;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.CourseId;

import java.util.Optional;

public interface CourseCatalog {

    void save(CourseCatalogEntry course);

    void deleteById(CourseId id);

    Optional<CourseCatalogEntry> findById(CourseId id);

    default CourseCatalogEntry getById(CourseId id) {
        return findById(id).orElseThrow();
    }
}
