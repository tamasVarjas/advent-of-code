package com.broomington.twentytwentyfour;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static com.broomington.util.FileReader.getInput;

public class Day1 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2024, Day1.class);

        final String[] lines = input.split("\n");
        final int size = lines.length;
        final int[] left = new int[size];
        final int[] right = new int[size];
        for (int i = 0; i < size; i++) {
            final String[] pair = lines[i].split("( )+");
            left[i] = Integer.parseInt(pair[0]);
            right[i] = Integer.parseInt(pair[1]);
        }
        Arrays.sort(left);
        Arrays.sort(right);

        System.out.println(getTotalDistance(left, right, size));
        System.out.println(getSimilarity(left, right));
    }

    private static int getTotalDistance(final int[] left, final int[] right, final int size) {
        return IntStream.range(0, size)
                .map(i -> Math.abs(left[i] - right[i]))
                .sum();
    }

    private static int getSimilarity(final int[] left, final int[] right) {
        return Arrays.stream(left)
                .map(item -> item * countOccurences(right, item))
                .sum();
    }

    private static int countOccurences(final int[] array, final int number) {
        return Math.toIntExact(Arrays.stream(array)
                .filter(item -> item == number)
                .count());
    }
}
