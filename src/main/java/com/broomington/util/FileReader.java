package com.broomington.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.lang.String.format;

public class FileReader {

    private FileReader() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> String getInput(T instance) throws IOException {
        Class<?> clazz = instance.getClass();
        final InputStream inputStream = clazz.getClassLoader().getResourceAsStream(format("%s-input.txt", clazz.getSimpleName()));

        return readFromInputStream(inputStream);
    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }

        return sb.toString();
    }
}
