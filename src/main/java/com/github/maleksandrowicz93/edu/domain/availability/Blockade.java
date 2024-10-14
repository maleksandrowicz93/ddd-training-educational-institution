package com.github.maleksandrowicz93.edu.domain.availability;

record Blockade(
        OwnerId blockedBy
) {

    static final Blockade NONE = new Blockade(null);

    static Blockade by(OwnerId ownerId) {
        return new Blockade(ownerId);
    }

    boolean blockedBy(OwnerId ownerId) {
        return !byNone() && blockedBy.equals(ownerId);
    }

    boolean byNone() {
        return NONE.equals(this);
    }
}
