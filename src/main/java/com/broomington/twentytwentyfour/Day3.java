package com.broomington.twentytwentyfour;

import com.broomington.util.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import static com.broomington.util.FileReader.getInput;

public class Day3 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2024, Day3.class);

        System.out.println(countSumOfMultiplications(input, Part.ONE));
        System.out.println(countSumOfMultiplications(input, Part.TWO));
    }

    private static int countSumOfMultiplications(final String input, final Part part) {
        if (part == Part.ONE) {
            return Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)")
                    .matcher(input)
                    .results()
                    .map(MatchResult::group)
                    .map(Day3::executeMultiplication)
                    .mapToInt(Integer::intValue)
                    .sum();
        } else if (part == Part.TWO) {
            final List<String> instructions = Pattern.compile("(do\\(\\)|mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\))")
                    .matcher(input)
                    .results()
                    .map(MatchResult::group)
                    .toList();

            return filterMultiplications(instructions).stream()
                    .map(Day3::executeMultiplication)
                    .mapToInt(Integer::intValue)
                    .sum();
        } else {
            throw new IllegalArgumentException("part not supported");
        }
    }

    private static List<String> filterMultiplications(final List<String> instructions) {
        final List<String> multiplications = new ArrayList<>();
        boolean doAdd = true;
        for (final String instruction : instructions) {
            if (instruction.equals("do()")) {
                doAdd = true;
            } else if (instruction.equals("don't()")) {
                doAdd = false;
            } else if (doAdd) {
                multiplications.add(instruction);
            }
        }
        return multiplications;
    }

    private static int executeMultiplication(final String multiplication) {
        final String[] numbers = multiplication.substring(4, multiplication.length() - 1).split(",");
        return Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
    }
}
