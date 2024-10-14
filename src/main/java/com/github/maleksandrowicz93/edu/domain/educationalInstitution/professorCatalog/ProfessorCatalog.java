package com.github.maleksandrowicz93.edu.domain.educationalInstitution.professorCatalog;

import com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared.ProfessorId;

import java.util.Optional;

public interface ProfessorCatalog {

    void save(ProfessorCatalogEntry professor);

    void deleteById(ProfessorId id);

    Optional<ProfessorCatalogEntry> findById(ProfessorId id);

    long countAll();

    default ProfessorCatalogEntry getBtId(ProfessorId id) {
        return findById(id).orElseThrow();
    }
}
