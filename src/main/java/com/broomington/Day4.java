package com.broomington;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.broomington.util.FileReader.getInput;

public class Day4 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(Day4.class);

        System.out.println(calculatePoints(getCards(input)));
        System.out.println(calculateNumberOfCards(getCards(input)));
    }

    private static int calculatePoints(final List<Card> cards) {
        return cards.stream()
                .map(Day4::calculatePoints)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static int calculatePoints(final Card card) {
        int nrOfHits = 0;
        int points = 0;
        for (final int nr : card.cardNrs) {
            if (isWinningNr(card, nr)) {
                if (nrOfHits == 0) {
                    points++;
                } else {
                    points *= 2;
                }
                nrOfHits++;
            }
        }

        return points;
    }

    private static boolean isWinningNr(final Card card, final int nr) {
        return Arrays.stream(card.winningNrs)
                .anyMatch(winningNr -> winningNr == nr);
    }

    private static List<Card> getCards(final String input) {
        return Arrays.stream(input.split("\n"))
                .map(line -> line.replaceAll("^Card\\s+\\d{0,3}:", "").split("\\|"))
                .map(splitLine -> new Card(
                        Arrays.stream(splitLine[0].trim().split("\\s+"))
                                .mapToInt(Integer::parseInt)
                                .toArray(),
                        Arrays.stream(splitLine[1].trim().split("\\s+"))
                                .mapToInt(Integer::parseInt)
                                .toArray()
                ))
                .toList();
    }

    private static int calculateNumberOfCards(final List<Card> originalCards) {
        final int[] cardAmounts = new int[originalCards.size()];
        Arrays.fill(cardAmounts, 1);
        for (int i = 0; i < originalCards.size(); i++) {
            final Card card = originalCards.get(i);
            final long hitCount = Arrays.stream(card.cardNrs)
                    .filter(nr -> Arrays.stream(card.winningNrs)
                            .anyMatch(winningNr -> winningNr == nr))
                    .count();
            for (int j = 0; j < hitCount; j++) {
                cardAmounts[i + j + 1] += cardAmounts[i];
            }
        }

        return Arrays.stream(cardAmounts)
                .sum();
    }

    private record Card(int[] winningNrs, int[] cardNrs) {

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Card card = (Card) o;

            return Arrays.equals(winningNrs, card.winningNrs) && Arrays.equals(cardNrs, card.cardNrs);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(winningNrs);
            result = 31 * result + Arrays.hashCode(cardNrs);

            return result;
        }

        @Override
        public String toString() {
            return "Card{" +
                    "winningNrs=" + Arrays.toString(winningNrs) +
                    ", cardNrs=" + Arrays.toString(cardNrs) +
                    '}';
        }
    }
}
