package com.broomington.twentytwentyfive;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.broomington.util.FileReader.getInput;

public class Day2 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2025, Day2.class);

        final List<Range> ranges = Arrays.stream(input.split(","))
                .map(pair -> {
                    final String[] limits = pair.trim().split("-");
                    return new Range(Long.parseLong(limits[0]), Long.parseLong(limits[1]));
                })
                .toList();

        final long part1 = part1(ranges);
        System.out.println(part1);

        final long part2 = part2(ranges);
        System.out.println(part2);
    }

    private static long part1(final List<Range> ranges) {
        long sum = 0L;
        for (final Range range : ranges) {
            for (long i = range.start; i <= range.end; i++) {
                final String numStr = Long.toString(i);
                final int length = numStr.length();
                if (length % 2 != 0) {
                    continue;
                }
                final int mid = length / 2;
                if (numStr.substring(0, mid).equals(numStr.substring(mid))) {
                    sum += i;
                }
            }
        }
        return sum;
    }

    private static long part2(final List<Range> ranges) {
        long sum = 0L;
        for (final Range range : ranges) {
            for (long i = range.start; i <= range.end; i++) {

                final String numStr = Long.toString(i);
                final int length = numStr.length();

                for (int rep = 2; rep <= length; rep++) {
                    if (length % rep != 0) continue;

                    final int chunkLen = length / rep;
                    final String chunk = numStr.substring(0, chunkLen);

                    boolean allMatch = true;

                    for (int k = 1; k < rep; k++) {
                        final int start = k * chunkLen;
                        if (!numStr.regionMatches(start, chunk, 0, chunkLen)) {
                            allMatch = false;
                            break;
                        }
                    }

                    if (allMatch) {
                        sum += i;
                        break;
                    }
                }
            }
        }
        return sum;
    }

    private record Range(long start, long end) {
    }
}
