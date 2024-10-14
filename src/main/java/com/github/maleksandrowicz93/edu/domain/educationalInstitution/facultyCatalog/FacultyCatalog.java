package com.github.maleksandrowicz93.edu.domain.educationalInstitution.facultyCatalog;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.FacultyId;

import java.util.Optional;

public interface FacultyCatalog {

    void save(FacultyCatalogEntry faculty);

    boolean existsByName(String name);

    Optional<FacultyCatalogEntry> findById(FacultyId id);

    default FacultyCatalogEntry getById(FacultyId id) {
        return findById(id).orElseThrow();
    }
}
