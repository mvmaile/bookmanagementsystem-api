package za.co.bookstore.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;
@Slf4j
public class ISBNGeneration {
    private static final String PREFIX = "978";
    private static final Random random = new Random();

    public static String generateISBN() {
        // Generate the first 12 digits (prefix + random numbers)
       StringBuilder sb = new StringBuilder(PREFIX);

        // Add 9 more random digits (total 12 digits before check digit)
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }

        String first12Digits = sb.toString();
        char checkDigit = calculateCheckDigit(first12Digits);

        return first12Digits + checkDigit;
    }

    private static char calculateCheckDigit(String first12Digits) {
        if (first12Digits.length() != 12) {
            throw new IllegalArgumentException("Must be 12 digits");
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(first12Digits.charAt(i));
            // Multiply by 1 or 3 alternately
            sum += (i % 2 == 0) ? digit * 1 : digit * 3;
        }
        int remainder = sum % 10;
        int checkDigit = (10 - remainder) % 10;
        return Character.forDigit(checkDigit, 10);
    }
    public static boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.length() != 13) {
            return false;
        }
        try {
            String first12Digits = isbn.substring(0, 12);
            char actualCheckDigit = isbn.charAt(12);
            char expectedCheckDigit = calculateCheckDigit(first12Digits);

            return actualCheckDigit == expectedCheckDigit;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
