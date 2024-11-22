package com.github.maleksandrowicz93.edu.common.infra;

public class OptimisticLockingException extends RuntimeException {
    public OptimisticLockingException(String message) {
        super(message);
    }
}
