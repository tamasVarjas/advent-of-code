package com.broomington;

import com.broomington.util.Part;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.broomington.util.FileReader.getInput;

public class Day6 {

    private static final String WHITESPACES = "\\s+";
    private static final String NUMBERS = "\\d+";
    private static final String NOT_NUMBERS = "\\D+";

    public static void main(final String[] args) throws IOException {
        final String input = getInput(Day6.class);

        System.out.println(getNrOfWinOptions(input, Part.ONE));
        System.out.println(getNrOfWinOptions(input, Part.TWO));
    }

    private static long getNrOfWinOptions(final String input, final Part part) {
        final Map<Part, List<Race>> racesByPart = Map.of(
                Part.ONE, getRaces(input),
                Part.TWO, List.of(getRace(input))
        );

        return racesByPart.get(part).stream()
                .mapToLong(race -> calculateWaysToWin(race.raceLength, race.maxDistance))
                .reduce(1, (result, waysToWin) -> result * waysToWin);
    }

    private static List<Race> getRaces(final String input) {
        final String[] lines = input.split("\n");
        final Integer[] raceLengths = Arrays.stream(lines[0].split(WHITESPACES))
                .filter(str -> str.matches(NUMBERS))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        final Integer[] distances = Arrays.stream(lines[1].split(WHITESPACES))
                .filter(str -> str.matches(NUMBERS))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        return IntStream.range(0, raceLengths.length)
                .boxed()
                .map(i -> new Race(raceLengths[i], distances[i]))
                .toList();
    }

    private static Race getRace(final String input) {
        final String[] lines = input.split("\n");
        final long raceLength = Long.parseLong(lines[0].replaceAll(NOT_NUMBERS, ""));
        final long maxDistance = Long.parseLong(lines[1].replaceAll(NOT_NUMBERS, ""));

        return new Race(raceLength, maxDistance);
    }

    private static long calculateWaysToWin(final long raceLength, final long maxDistance) {
        return IntStream.rangeClosed(0, (int) raceLength)
                .filter(chargeTime -> {
                    final long speed = chargeTime;
                    final long remainingTime = raceLength - chargeTime;
                    final long possibleDistance = speed * remainingTime;
                    return possibleDistance > maxDistance;
                })
                .count();
    }

    private record Race(long raceLength, long maxDistance) {
    }
}
