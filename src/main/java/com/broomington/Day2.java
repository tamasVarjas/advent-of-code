package com.broomington;

import com.broomington.util.Part;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.broomington.util.FileReader.getInput;

public class Day2 {

    public static final int MAX_RED = 12;
    public static final int MAX_BLUE = 14;
    public static final int MAX_GREEN = 13;

    public static void main(final String[] args) throws IOException {
        final Day2 day2 = new Day2();
        final String input = getInput(day2);

        System.out.println(day2.getSumOfPossibleGameIds(input, Part.ONE));
        System.out.println(day2.getSumOfPossibleGameIds(input, Part.TWO));
    }

    private int getSumOfPossibleGameIds(final String input, final Part part) {
        final String gameRegex = "Game\\s([1-9]|[1-9][0-9]|100):";

        int sum = 0;
        final String[] strings = (input.replaceAll(gameRegex, "")).split("\n");
        for (int i = 0; i < strings.length; i++) {
            final int gameId = i + 1;
            final String game = strings[i];
            final String[] split = game.split(";");
            int red = 0;
            int blue = 0;
            int green = 0;
            for (final String sample : split) {
                final String[] balls = sample.split(",");
                final String nrRegex = "(\\d+)";
                final Pattern pattern = Pattern.compile(nrRegex);
                for (final String ball : balls) {
                    final Matcher matcher = pattern.matcher(ball);
                    if (ball.contains("red") && matcher.find()) {
                        final int redInSample = Integer.parseInt(matcher.group(1));
                        red = Math.max(redInSample, red);
                    } else if (ball.contains("blue") && matcher.find()) {
                        final int blueInSample = Integer.parseInt(matcher.group(1));
                        blue = Math.max(blueInSample, blue);
                    } else if (ball.contains("green") && matcher.find()) {
                        final int greenInSample = Integer.parseInt(matcher.group(1));
                        green = Math.max(greenInSample, green);
                    }
                }
            }
            if (part == Part.ONE) {
                sum += red <= MAX_RED && blue <= MAX_BLUE && green <= MAX_GREEN ? gameId : 0;
            } else if (part == Part.TWO) {
                sum += red * blue * green;
            }
        }

        return sum;
    }
}
