package com.broomington.twentytwentyfour;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.broomington.util.FileReader.getInput;

public class Day5 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2024, Day5.class);
        final var rules = Arrays.stream(input.split("\n\n")[0]
                        .split("\n"))
                .map(Day5::toRule)
                .toList();
        final var updates = Arrays.stream(input.split("\n\n")[1]
                .split("\n"))
                .map(Day5::toUpdate)
                .toList();

        partOne(updates, rules);
        //partTwo(updates, rules);
    }

    private static void partOne(final List<Update> updates, final List<Rule> rules) {
        final int sumOfCorrectMiddles = updates.stream()
                .filter(update -> isCorrect(update, rules))
                .map(Day5::getMiddlePage)
                .mapToInt(PageUpdate::page)
                .sum();

        System.out.println(sumOfCorrectMiddles);
    }

    private static PageUpdate getMiddlePage(final Update update) {
        final int middleIndex = (update.pageUpdates.size() - 1) / 2;
        return update.getPageUpdateByIndex(middleIndex);
    }

    /*
    private static void partTwo(final List<Map<Integer, Integer>> pageByIndexPerUpdate, final List<Rule> rules) {
        final List<Map<Integer, Integer>> incorrectUpdates = new ArrayList<>();
        for (final Map<Integer, Integer> update : pageByIndexPerUpdate) {
            if (!isCorrect(rules, update)) {
                incorrectUpdates.add(update);
            }
        }

        System.out.println("foo");
    }
     */

    private static boolean isCorrect(final Update update, final List<Rule> rules) {
        return rules.stream()
                .filter(rule -> isRuleRelevant(update, rule))
                .allMatch(rule -> isRuleSatisfied(update, rule));
    }

    private static boolean isRuleRelevant(final Update update, final Rule rule) {
        final var pageUpdates = update.pageUpdates;
        return pageUpdates.stream()
                .anyMatch(pageUpdate -> pageUpdate.page() == rule.first)
                && pageUpdates.stream()
                .anyMatch(pageUpdate -> pageUpdate.page == rule.second);
    }

    private static boolean isRuleSatisfied(final Update update, final Rule rule) {
        return update.getPageUpdateByPage(rule.first).index < update.getPageUpdateByPage(rule.second).index;
    }

    private static Rule toRule(final String ruleStr) {
        final var ruleArr = ruleStr.split("\\|");
        return new Rule(
                Integer.parseInt(ruleArr[0]),
                Integer.parseInt(ruleArr[1])
        );
    }

    private static Update toUpdate(final String pageUpdateStr) {
        final var pageUpdateArr = pageUpdateStr.split(",");
        final var pageUpdates = IntStream.range(0, pageUpdateArr.length)
                .mapToObj(i -> new PageUpdate(Integer.parseInt(pageUpdateArr[i]), i))
                .toList();
        return new Update(pageUpdates);
    }

    private record Rule(int first, int second) {
    }

    private record PageUpdate(int page, int index) {
    }

    private record Update(List<PageUpdate> pageUpdates) {

        private PageUpdate getPageUpdateByPage(final int page) {
            return pageUpdates.stream()
                    .filter(pageUpdate -> pageUpdate.page == page)
                    .findFirst()
                    .orElseThrow();
        }

        private PageUpdate getPageUpdateByIndex(final int index) {
            return pageUpdates.stream()
                    .filter(pageUpdate -> pageUpdate.index == index)
                    .findFirst()
                    .orElseThrow();
        }
    }
}
