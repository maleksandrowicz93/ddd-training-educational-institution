package com.github.maleksandrowicz93.edu.common.name;

import java.util.List;

public record FullName(
        String firstName,
        List<String> middleNames,
        String lastName
) {

    public FullName(String firstName, String lastName) {
        this(firstName, List.of(), lastName);
    }
}
