package com.broomington.twentytwentythree;

import com.broomington.util.Part;

import java.io.IOException;
import java.util.Arrays;

import static com.broomington.util.FileReader.getInput;
import static java.lang.Character.isDigit;
import static java.lang.String.format;

public class Day1 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2023, Day1.class);

        System.out.println(getSum(input, Part.ONE));
        System.out.println(getSum(input, Part.TWO));
    }

    private static int getSum(final String input, final Part part) {
        final String finalInput = part == Part.ONE ?
                input :
                input
                        .replace("one", "one1one")
                        .replace("two", "two2two")
                        .replace("three", "three3three")
                        .replace("four", "four4four")
                        .replace("five", "five5five")
                        .replace("six", "six6six")
                        .replace("seven", "seven7seven")
                        .replace("eight", "eight8eight")
                        .replace("nine", "nine9nine");

        return Arrays.stream(finalInput.split("\n"))
                .map(String::toCharArray)
                .map(chars -> {
                    int first = Integer.MIN_VALUE;
                    int last = Integer.MIN_VALUE;
                    for (final char ch : chars) {
                        if (isDigit(ch)) {
                            if (first == Integer.MIN_VALUE) {
                                first = Character.getNumericValue(ch);
                            }
                            last = Character.getNumericValue(ch);
                        }
                    }
                    return Integer.parseInt(format("%d%d", first, last));
                })
                .mapToInt(Integer::intValue)
                .sum();
    }
}
