package com.broomington.twentytwentyfive;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.broomington.util.FileReader.getInput;
import static java.util.Comparator.comparingLong;

public class Day5 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2025, Day5.class);

        final List<Range> ranges = Arrays.stream(input.split("\n"))
                .filter(line -> line.contains("-"))
                .map(line -> new Range(Long.parseLong(line.split("-")[0]), Long.parseLong(line.split("-")[1])))
                .toList();

        final List<Long> ingredients = Arrays.stream(input.split("\n"))
                .filter(line -> !line.isEmpty() && !line.contains("-"))
                .map(Long::parseLong)
                .toList();

        final int part1 = part1(ingredients, ranges);
        System.out.println(part1);

        final long part2 = part2(ranges);
        System.out.println(part2);
    }

    private static int part1(final List<Long> ingredients, final List<Range> ranges) {
        int numberOfFreshIngredients = 0;

        for (final Long ingredient : ingredients) {
            for (final Range range : ranges) {
                if (ingredient >= range.start && ingredient <= range.end) {
                    numberOfFreshIngredients++;
                    break;
                }
            }
        }
        return numberOfFreshIngredients;
    }

    private static long part2(final List<Range> ranges) {
        final List<Range> sortedRanges = ranges.stream()
                .sorted(comparingLong(range -> range.start))
                .toList();

        long numberOfFreshIngredients = 0;
        long currentStart = sortedRanges.get(0).start;
        long currentEnd = sortedRanges.get(0).end;

        for (int i = 1; i < sortedRanges.size(); i++) {
            final Range range = sortedRanges.get(i);

            if (range.start <= currentEnd + 1) {
                // Overlap → extend range
                currentEnd = Math.max(currentEnd, range.end);
            } else {
                // No overlap → add previous range length
                numberOfFreshIngredients += currentEnd - currentStart + 1;

                // Start a new merged range
                currentStart = range.start;
                currentEnd = range.end;
            }
        }

        // Add last merged range
        numberOfFreshIngredients += currentEnd - currentStart + 1;

        return numberOfFreshIngredients;
    }

    private record Range(long start, long end) {
    }
}
