package com.broomington.twentytwentyfive;

import java.io.IOException;

import static com.broomington.util.FileReader.getInput;

public class Day2 {

    public static void main(String[] args) throws IOException {
        final String input = getInput(2025, Day2.class);
    }

    private record Range(int start, int end) {
    }
}
