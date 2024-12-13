package com.broomington.twentytwentyfour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.broomington.util.FileReader.getInput;
import static java.util.stream.Collectors.toList;

public class Day5 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2024, Day5.class);
        final String[] rules = input.split("\n\n")[0]
                .split("\n");
        final String[] updates = input.split("\n\n")[1]
                .split("\n");

        final List<Map<Integer, Integer>> pageByIndexPerUpdate = new ArrayList<>();
        for (int i = 0; i < updates.length; i++) {
            final Map<Integer, Integer> update = new HashMap<>();
            pageByIndexPerUpdate.add(update);
            final String[] pages = updates[i].split(",");
            for (int j = 0; j < pages.length; j++) {
                update.put(Integer.valueOf(pages[j]), j);
            }
        }

        final List<Map<Integer, Integer>> correctUpdates = new ArrayList<>();
        for (final Map<Integer, Integer> update : pageByIndexPerUpdate) {
            boolean isCorrect = true;
            for (final String rule : rules) {
                final Integer first = Integer.valueOf(rule.split("\\|")[0]);
                final Integer second = Integer.valueOf(rule.split("\\|")[1]);
                if (update.containsKey(first) && update.containsKey(second) && update.get(first) > update.get(second)) {
                    isCorrect = false;
                    break;
                }
            }
            if (isCorrect) {
                correctUpdates.add(update);
            }
        }

        int sumOfCorrectMiddles = 0;
        for (final Map<Integer, Integer> update : correctUpdates) {
            final int index = (update.size() - 1) / 2;
            for (final Map.Entry<Integer, Integer> entry : update.entrySet()) {
                if (entry.getValue() == index) {
                    sumOfCorrectMiddles += entry.getKey();
                }
            }
        }

        System.out.println(sumOfCorrectMiddles);
    }
}
