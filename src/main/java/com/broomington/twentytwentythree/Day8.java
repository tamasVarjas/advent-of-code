package com.broomington.twentytwentythree;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.broomington.util.FileReader.getInput;

public class Day8 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2023, Day8.class);

        System.out.println(countSteps(input));
    }

    private static int countSteps(final String input) {
        final String[] lines = input.split("\n");
        final char[] steps = lines[0].toCharArray();
        final Map<String, Node> nodes = getNodes(lines);
        final Node aaa = nodes.get("AAA");
        final Node zzz = nodes.get("ZZZ");

        Node current = aaa;
        int stepCount = 0;
        while (current != zzz) {
            final char step = steps[stepCount % steps.length];
            if (step == 'L') {
                current = nodes.get(current.left);
            } else if (step == 'R') {
                current = nodes.get(current.right);
            }
            stepCount++;
        }

        return stepCount;
    }

    private static Map<String, Node> getNodes(final String[] lines) {
        return Arrays.stream(lines)
                .skip(2)
                .collect(Collectors.toMap(
                        line -> line.split("=")[0].trim(),
                        line -> {
                            final String[] parts = line.split("=");
                            final String instructionsStr = parts[1].trim();
                            final String[] leftAndRight = instructionsStr.substring(1, instructionsStr.length() - 1).split(",");
                            return new Node(
                                    parts[0].trim(),
                                    leftAndRight[0],
                                    leftAndRight[1].trim()
                            );
                        }
                ));
    }

    private record Node(String name, String left, String right) {
    }
}
