package com.github.maleksandrowicz93.edu.common.capacity;

public record Capacity(
        int value
) {

    public Capacity {
        if (value < 0) {
            throw new IllegalArgumentException("Capacity value cannot be less than 0");
        }
    }

    public static Capacity of(int value) {
        return new Capacity(value);
    }
}
