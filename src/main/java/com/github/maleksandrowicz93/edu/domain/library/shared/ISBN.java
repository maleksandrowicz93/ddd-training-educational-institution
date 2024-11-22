package com.github.maleksandrowicz93.edu.domain.library.shared;

public record ISBN(String value) {

    public ISBN {
        if (value == null || !isValidISBN(value)) {
            throw new IllegalArgumentException("Invalid ISBN: " + value);
        }
    }

    private static boolean isValidISBN(String isbn) {
        isbn = isbn.replace("-", "").replace(" ", "");

        if (isbn.length() == 10) {
            return isValidISBN10(isbn);
        } else if (isbn.length() == 13) {
            return isValidISBN13(isbn);
        }
        return false;
    }

    private static boolean isValidISBN10(String isbn) {
        if (!isbn.matches("\\d{9}[\\dX]")) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        char checkDigit = isbn.charAt(9);
        sum += (checkDigit == 'X') ? 10 : (checkDigit - '0');
        return sum % 11 == 0;
    }

    private static boolean isValidISBN13(String isbn) {
        if (!isbn.matches("\\d{13}")) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn.charAt(i) - '0';
            sum += digit * (i % 2 == 0 ? 1 : 3);
        }
        int checkDigit = 10 - (sum % 10);
        if (checkDigit == 10) checkDigit = 0;
        return checkDigit == (isbn.charAt(12) - '0');
    }
}

