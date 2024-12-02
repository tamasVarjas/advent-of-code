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

    public static String getInput(final int year, final Class<?> clazz) throws IOException {
        final InputStream inputStream = clazz.getClassLoader().getResourceAsStream(format("%d/%s-input.txt", year, clazz.getSimpleName()));

        return readFromInputStream(inputStream);
    }

    private static String readFromInputStream(final InputStream inputStream) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }

        return sb.toString();
    }
}
