package com.github.maleksandrowicz93.edu.domain.educationalInstitution.shared;

public record Vacancies(
        int count
) {

    public Vacancies {
        if (count < 0) {
            throw new IllegalArgumentException("Vacancies count cannot be less than 0");
        }
    }
}
