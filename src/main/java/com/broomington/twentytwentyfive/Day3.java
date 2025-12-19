package com.broomington.twentytwentyfive;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.broomington.util.FileReader.getInput;

public class Day3 {

    private static final int NUMBER_OF_DIGITS = 12;

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2025, Day3.class);

        final List<String> lines = Arrays.stream(input.split("\n")).toList();

        final int part1 = part1(lines);
        System.out.println(part1);

        final long part2 = part2(lines);
        System.out.println(part2);
    }

    private static int part1(final List<String> lines) {
        int totalSum = 0;
        for (final String line : lines) {
            int left = 0, right = 0;
            int lineSum = 0;
            for (int i = 0; i < line.length(); i++) {
                final int current = Integer.parseInt(line.substring(i, i + 1));
                if (i != line.length() - 1 && current > left) {
                    left = current;
                    right = 0;
                } else if (left != 0 && current > right) {
                    right = current;
                }
            }
            lineSum = left * 10 + right;
            totalSum += lineSum;
        }
        return totalSum;
    }

    private static long part2(final List<String> lines) {
        long totalSum = 0;
        for  (final String line : lines) {
            final int length = line.length();
            if (length < NUMBER_OF_DIGITS) {
                throw new IllegalArgumentException("Line too short for k = " + NUMBER_OF_DIGITS + ": " + line);
            }

            long lineSum = 0L;
            int start = 0;

            for (int pos = 0; pos < NUMBER_OF_DIGITS; pos++) {
                final int digitsLeft = NUMBER_OF_DIGITS - pos;
                final int lastAllowedIndex = length - digitsLeft;

                char maxDigit = '0';
                int maxIdx = start;

                for (int i = start; i <= lastAllowedIndex; i++) {
                    final char d = line.charAt(i);
                    if (d > maxDigit) {
                        maxDigit = d;
                        maxIdx = i;
                        if (maxDigit == '9') {
                            // can't get better than 9, early exit for this position
                            break;
                        }
                    }
                }

                lineSum = lineSum * 10 + (maxDigit - '0');
                start = maxIdx + 1;
            }
            totalSum += lineSum;
        }
        return totalSum;
    }
}
