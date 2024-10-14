package com.github.maleksandrowicz93.edu.common.result;

public record Result(
        boolean isSuccessful,
        String reason
) {

    public static Result successful() {
        return new Result(true, "SUCCESS");
    }

    public static Result failed(String reason) {
        return new Result(false, reason);
    }

    public boolean isFailed() {
        return !isSuccessful;
    }
}
