package com.github.maleksandrowicz93.edu.domain.library.bookInventory;

import com.github.maleksandrowicz93.edu.common.name.FullName;

import java.util.List;

public record Authors(
        List<FullName> all
) {

    public Authors(List<FullName> all) {
        if (all == null || all.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one author");
        }
        this.all = List.copyOf(all);
    }

    public static Authors singleOne(FullName fullName) {
        return new Authors(List.of(fullName));
    }

    public boolean single() {
        return all.size() == 1;
    }

    public boolean multiple() {
        return all.size() > 1;
    }

    public FullName onlyOne() {
        return all.getFirst();
    }
}
