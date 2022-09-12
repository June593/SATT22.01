package com.railway.common;

import java.util.UUID;

public class Helper {
    public static String getUniqueID(int numberOfCharacters) {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString().substring(0, numberOfCharacters);
    }

    public static String getUniqueEmail() {
        return getUniqueID(8) + "@gmail.com";
    }

    public static int getRandomIndex(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
