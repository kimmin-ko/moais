package com.moais.todo.util;

import java.util.Random;

public final class StringGenerator {

    private static final Random RANDOM = new Random();

    private StringGenerator() {
    }

    public static String generate(int size) {
        if (size <= 0) {
            return "";
        }

        int leftLimit = 97; // a
        int rightLimit = 122; // z
        return RANDOM.ints(leftLimit, rightLimit + 1)
                .limit(size)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
