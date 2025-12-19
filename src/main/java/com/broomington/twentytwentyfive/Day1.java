package com.broomington.twentytwentyfive;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.broomington.util.FileReader.getInput;

public class Day1 {

    public static void main(String[] args) throws IOException {
        final String input = getInput(2025, Day1.class);

        List<Turn> turns = Arrays.stream(input.split("\n"))
                .map(line -> new Turn(
                        line.charAt(0),
                        Integer.parseInt(line.substring(1))
                ))
                .toList();

        int current = 50;
        int password = 0;

        part1(turns, current, password);

        current = 50;
        password = 0;

        for (Turn turn : turns) {
            if (turn.direction == 'L') {
                for (int i = 0; i < turn.distance; i++) {
                    if (current == 0) {
                        password++;
                        current = 99;
                    } else {
                        current--;
                    }
                }
            } else {
                for (int i = 0; i < turn.distance; i++) {
                    if (current == 0) {
                        password++;
                    }
                    if (current == 99) {
                        current = 0;
                    } else {
                        current++;
                    }
                }
            }
        }
        System.out.println(password);
    }

    private static void part1(List<Turn> turns, int current, int password) {
        for (Turn turn : turns) {
            if (turn.direction == 'L') {
                for (int i = 0; i < turn.distance; i++) {
                    if (current == 0) {
                        current = 99;
                    } else {
                        current--;
                    }
                }
            } else {
                for (int i = 0; i < turn.distance; i++) {
                    if (current == 99) {
                        current = 0;
                    } else {
                        current++;
                    }
                }
            }
            if (current == 0) {
                password++;
            }
        }
        System.out.println(password);
    }

    private record Turn(char direction, int distance) {
    }
}
