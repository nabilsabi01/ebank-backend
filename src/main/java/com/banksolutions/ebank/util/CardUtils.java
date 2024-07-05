package com.banksolutions.ebank.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CardUtils {

    private static final Random random = new Random();

    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
            if ((i + 1) % 4 == 0 && i != 15) {
                cardNumber.append(" ");
            }
        }
        return cardNumber.toString();
    }

    public static String generateExpirationDate(int yearsToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate expirationDate = currentDate.plusYears(yearsToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
        return expirationDate.format(formatter);
    }
}
