package com.broomington.twentytwentyfour;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.broomington.util.FileReader.getInput;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class Day4 {

    private static final Set<Word> WORDS = new HashSet<>();
    private static final Set<Word> X_WORDS = new HashSet<>();

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2024, Day4.class);

        final String[] lines = input.split("\n");
        final int numOfRows = lines.length;
        final int numOfColumns = lines[0].length();
        final char[][] wordSearch = new char[numOfRows][numOfColumns];

        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfColumns; c++) {
                wordSearch[r][c] = lines[r].charAt(c);
            }
        }

//        printWordSearch(wordSearch);

        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfColumns; c++) {
                try {
                    final Coordinate coor = new Coordinate(r, c);
                    isHitHorizFwd(wordSearch, wordSearch[r][c], coor);
                    isHitHorizBwd(wordSearch, wordSearch[r][c], coor);
                    isHitVertDn(wordSearch, wordSearch[r][c], coor);
                    isHitVertUp(wordSearch, wordSearch[r][c], coor);
                    isHitDiagDnFwd(wordSearch, wordSearch[r][c], coor);
                    isHitDiagUpFwd(wordSearch, wordSearch[r][c], coor);
                    isHitDiagUpBwd(wordSearch, wordSearch[r][c], coor);
                    isHitDiagDnBwd(wordSearch, wordSearch[r][c], coor);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // move on
                }
            }
        }

//        for (Word w : WORDS) {
//            System.out.printf("(%d, %d), (%d, %d), (%d, %d), (%d, %d)%n", w.coordinates.stream().toList().get(0).r, w.coordinates.stream().toList().get(0).c, w.coordinates.stream().toList().get(1).r, w.coordinates.stream().toList().get(1).c, w.coordinates.stream().toList().get(2).r, w.coordinates.stream().toList().get(2).c, w.coordinates.stream().toList().get(3).r, w.coordinates.stream().toList().get(3).c);
//        }
//
//        printResults(WORDS, wordSearch);

        System.out.println(WORDS.size());

        for (int r = 0; r < numOfRows; r++) {
            for (int c = 0; c < numOfColumns; c++) {
                try {
                    final Coordinate coor = new Coordinate(r, c);
                    isHitDiagDnFwdMas(wordSearch, wordSearch[r][c], coor);
                    isHitDiagUpFwdMas(wordSearch, wordSearch[r][c], coor);
                    isHitDiagUpBwdMas(wordSearch, wordSearch[r][c], coor);
                    isHitDiagDnBwdMas(wordSearch, wordSearch[r][c], coor);
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // move on
                }
            }
        }

        final Set<Word> x = X_WORDS.stream()
                .flatMap(word -> X_WORDS.stream()
                        .filter(w -> !w.equals(word) && w.a.equals(word.a))
                )
                .collect(toUnmodifiableSet());

        System.out.println(x.size() / 2);
    }

    private static void printWordSearch(final char[][] wordSearch) {
        final int numOfRows = wordSearch.length;
        final int numOfColumns = wordSearch[0].length;

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfColumns; j++) {
                System.out.print(wordSearch[i][j]);
            }
            System.out.println();
        }
    }

    private static void isHitHorizFwd(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r][coord.c + 1] == 'M' && wS[coord.r][coord.c + 2] == 'A' && wS[coord.r][coord.c + 3] == 'S') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r, coord.c + 1), new Coordinate(coord.r, coord.c + 2), new Coordinate(coord.r, coord.c + 3)));
                }
            } else if (ch == 'M') {
                if (wS[coord.r][coord.c - 1] == 'X' && wS[coord.r][coord.c + 1] == 'A' && wS[coord.r][coord.c + 2] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r, coord.c - 1), coord, new Coordinate(coord.r, coord.c + 1), new Coordinate(coord.r, coord.c + 2)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r][coord.c - 2] == 'X' && wS[coord.r][coord.c - 1] == 'M' && wS[coord.r][coord.c + 1] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r, coord.c - 2), new Coordinate(coord.r, coord.c - 1), coord, new Coordinate(coord.r, coord.c + 1)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r][coord.c - 3] == 'X' && wS[coord.r][coord.c - 2] == 'M' && wS[coord.r][coord.c - 1] == 'A') {
                    WORDS.add(new Word(new Coordinate(coord.r, coord.c - 3), new Coordinate(coord.r, coord.c - 2), new Coordinate(coord.r, coord.c - 1), coord));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitHorizBwd(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r][coord.c - 1] == 'M' && wS[coord.r][coord.c - 2] == 'A' && wS[coord.r][coord.c - 3] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r, coord.c - 3), new Coordinate(coord.r, coord.c - 2), new Coordinate(coord.r, coord.c - 1), coord));
                }
            } else if (ch == 'M') {
                if (wS[coord.r][coord.c + 1] == 'X' && wS[coord.r][coord.c - 1] == 'A' && wS[coord.r][coord.c - 2] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r, coord.c - 2), new Coordinate(coord.r, coord.c - 1), coord, new Coordinate(coord.r, coord.c + 1)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r][coord.c + 2] == 'X' && wS[coord.r][coord.c + 1] == 'M' && wS[coord.r][coord.c - 1] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r, coord.c - 1), coord, new Coordinate(coord.r, coord.c + 1), new Coordinate(coord.r, coord.c + 2)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r][coord.c + 3] == 'X' && wS[coord.r][coord.c + 2] == 'M' && wS[coord.r][coord.c + 1] == 'A') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r, coord.c + 1), new Coordinate(coord.r, coord.c + 2), new Coordinate(coord.r, coord.c + 3)));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitVertDn(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r + 1][coord.c] == 'M' && wS[coord.r + 2][coord.c] == 'A' && wS[coord.r + 3][coord.c] == 'S') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r + 1, coord.c), new Coordinate(coord.r + 2, coord.c), new Coordinate(coord.r + 3, coord.c)));
                }
            } else if (ch == 'M') {
                if (wS[coord.r - 1][coord.c] == 'X' && wS[coord.r + 1][coord.c] == 'A' && wS[coord.r + 2][coord.c] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 1, coord.c), coord, new Coordinate(coord.r + 1, coord.c), new Coordinate(coord.r + 2, coord.c)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r - 2][coord.c] == 'X' && wS[coord.r - 1][coord.c] == 'M' && wS[coord.r + 1][coord.c] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 2, coord.c), new Coordinate(coord.r - 1, coord.c), coord, new Coordinate(coord.r + 1, coord.c)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r - 3][coord.c] == 'X' && wS[coord.r - 2][coord.c] == 'M' && wS[coord.r - 1][coord.c] == 'A') {
                    WORDS.add(new Word(new Coordinate(coord.r - 3, coord.c), new Coordinate(coord.r - 2, coord.c), new Coordinate(coord.r - 1, coord.c), coord));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitVertUp(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r - 1][coord.c] == 'M' && wS[coord.r - 2][coord.c] == 'A' && wS[coord.r - 3][coord.c] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 3, coord.c), new Coordinate(coord.r - 2, coord.c), new Coordinate(coord.r - 1, coord.c), coord));
                }
            } else if (ch == 'M') {
                if (wS[coord.r + 1][coord.c] == 'X' && wS[coord.r - 1][coord.c] == 'A' && wS[coord.r - 2][coord.c] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 2, coord.c), new Coordinate(coord.r - 1, coord.c), coord, new Coordinate(coord.r + 1, coord.c)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r + 2][coord.c] == 'X' && wS[coord.r + 1][coord.c] == 'M' && wS[coord.r - 1][coord.c] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 1, coord.c), coord, new Coordinate(coord.r + 1, coord.c), new Coordinate(coord.r + 2, coord.c)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r + 3][coord.c] == 'X' && wS[coord.r + 2][coord.c] == 'M' && wS[coord.r + 1][coord.c] == 'A') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r + 1, coord.c), new Coordinate(coord.r + 2, coord.c), new Coordinate(coord.r + 3, coord.c)));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagDnFwd(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r + 1][coord.c + 1] == 'M' && wS[coord.r + 2][coord.c + 2] == 'A' && wS[coord.r + 3][coord.c + 3] == 'S') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r + 1, coord.c + 1), new Coordinate(coord.r + 2, coord.c + 2), new Coordinate(coord.r + 3, coord.c + 3)));
                }
            } else if (ch == 'M') {
                if (wS[coord.r - 1][coord.c - 1] == 'X' && wS[coord.r + 1][coord.c + 1] == 'A' && wS[coord.r + 2][coord.c + 2] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 1, coord.c - 1), coord, new Coordinate(coord.r + 1, coord.c + 1), new Coordinate(coord.r + 2, coord.c + 2)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r - 2][coord.c - 2] == 'X' && wS[coord.r - 1][coord.c - 1] == 'M' && wS[coord.r + 1][coord.c + 1] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 2, coord.c - 2), new Coordinate(coord.r - 1, coord.c - 1), coord, new Coordinate(coord.r + 1, coord.c + 1)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r - 3][coord.c - 3] == 'X' && wS[coord.r - 2][coord.c - 2] == 'M' && wS[coord.r - 1][coord.c - 1] == 'A') {
                    WORDS.add(new Word(new Coordinate(coord.r - 3, coord.c - 3), new Coordinate(coord.r - 2, coord.c - 2), new Coordinate(coord.r - 1, coord.c - 1), coord));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagUpFwd(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r - 1][coord.c + 1] == 'M' && wS[coord.r - 2][coord.c + 2] == 'A' && wS[coord.r - 3][coord.c + 3] == 'S') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r - 1, coord.c + 1), new Coordinate(coord.r - 2, coord.c + 2), new Coordinate(coord.r - 3, coord.c + 3)));
                }
            } else if (ch == 'M') {
                if (wS[coord.r + 1][coord.c - 1] == 'X' && wS[coord.r - 1][coord.c + 1] == 'A' && wS[coord.r - 2][coord.c + 2] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r + 1, coord.c - 1), coord, new Coordinate(coord.r - 1, coord.c + 1), new Coordinate(coord.r - 2, coord.c + 2)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r + 2][coord.c - 2] == 'X' && wS[coord.r + 1][coord.c - 1] == 'M' && wS[coord.r - 1][coord.c + 1] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r + 2, coord.c - 2), new Coordinate(coord.r + 1, coord.c - 1), coord, new Coordinate(coord.r - 1, coord.c + 1)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r + 3][coord.c - 3] == 'X' && wS[coord.r + 2][coord.c - 2] == 'M' && wS[coord.r + 1][coord.c - 1] == 'A') {
                    WORDS.add(new Word(new Coordinate(coord.r + 3, coord.c - 3), new Coordinate(coord.r + 2, coord.c - 2), new Coordinate(coord.r + 1, coord.c - 1), coord));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagUpBwd(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r - 1][coord.c - 1] == 'M' && wS[coord.r - 2][coord.c - 2] == 'A' && wS[coord.r - 3][coord.c - 3] == 'S') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r - 1, coord.c - 1), new Coordinate(coord.r - 2, coord.c - 2), new Coordinate(coord.r - 3, coord.c - 3)));
                }
            } else if (ch == 'M') {
                if (wS[coord.r + 1][coord.c + 1] == 'X' && wS[coord.r - 1][coord.c - 1] == 'A' && wS[coord.r - 2][coord.c - 2] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r + 1, coord.c + 1), coord, new Coordinate(coord.r - 1, coord.c - 1), new Coordinate(coord.r - 2, coord.c - 2)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r + 2][coord.c + 2] == 'X' && wS[coord.r + 1][coord.c + 1] == 'M' && wS[coord.r - 1][coord.c - 1] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r + 2, coord.c + 2), new Coordinate(coord.r + 1, coord.c + 1), coord, new Coordinate(coord.r - 1, coord.c - 1)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r + 3][coord.c + 3] == 'X' && wS[coord.r + 2][coord.c + 2] == 'M' && wS[coord.r + 1][coord.c + 1] == 'A') {
                    WORDS.add(new Word(new Coordinate(coord.r + 3, coord.c + 3), new Coordinate(coord.r + 2, coord.c + 2), new Coordinate(coord.r + 1, coord.c + 1), coord));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagDnBwd(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'X') {
                if (wS[coord.r + 1][coord.c - 1] == 'M' && wS[coord.r + 2][coord.c - 2] == 'A' && wS[coord.r + 3][coord.c - 3] == 'S') {
                    WORDS.add(new Word(coord, new Coordinate(coord.r + 1, coord.c - 1), new Coordinate(coord.r + 2, coord.c - 2), new Coordinate(coord.r + 3, coord.c - 3)));
                }
            } else if (ch == 'M') {
                if (wS[coord.r - 1][coord.c + 1] == 'X' && wS[coord.r + 1][coord.c - 1] == 'A' && wS[coord.r + 2][coord.c - 2] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 1, coord.c + 1), coord, new Coordinate(coord.r + 1, coord.c - 1), new Coordinate(coord.r + 2, coord.c - 2)));
                }
            } else if (ch == 'A') {
                if (wS[coord.r - 2][coord.c + 2] == 'X' && wS[coord.r - 1][coord.c + 1] == 'M' && wS[coord.r + 1][coord.c - 1] == 'S') {
                    WORDS.add(new Word(new Coordinate(coord.r - 2, coord.c + 2), new Coordinate(coord.r - 1, coord.c + 1), coord, new Coordinate(coord.r + 1, coord.c - 1)));
                }
            } else if (ch == 'S') {
                if (wS[coord.r - 3][coord.c + 3] == 'X' && wS[coord.r - 2][coord.c + 2] == 'M' && wS[coord.r - 1][coord.c + 1] == 'A') {
                    WORDS.add(new Word(new Coordinate(coord.r - 3, coord.c + 3), new Coordinate(coord.r - 2, coord.c + 2), new Coordinate(coord.r - 1, coord.c + 1), coord));
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagDnFwdMas(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'M') {
                if (wS[coord.r + 1][coord.c + 1] == 'A' && wS[coord.r + 2][coord.c + 2] == 'S') {
                    final Coordinate a = new Coordinate(coord.r + 1, coord.c + 1);
                    final Word word = new Word(coord, a, new Coordinate(coord.r + 2, coord.c + 2));
                    word.a = a;
                    X_WORDS.add(word);
                }
            } else if (ch == 'A') {
                if (wS[coord.r - 1][coord.c - 1] == 'M' && wS[coord.r + 1][coord.c + 1] == 'S') {
                    final Word word = new Word(new Coordinate(coord.r - 1, coord.c - 1), coord, new Coordinate(coord.r + 1, coord.c + 1));
                    word.a = coord;
                    X_WORDS.add(word);
                }
            } else if (ch == 'S') {
                if (wS[coord.r - 2][coord.c - 2] == 'M' && wS[coord.r - 1][coord.c - 1] == 'A') {
                    final Coordinate a = new Coordinate(coord.r - 1, coord.c - 1);
                    final Word word = new Word(new Coordinate(coord.r - 2, coord.c - 2), a, coord);
                    word.a = a;
                    X_WORDS.add(word);
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagUpFwdMas(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'M') {
                if (wS[coord.r - 1][coord.c + 1] == 'A' && wS[coord.r - 2][coord.c + 2] == 'S') {
                    final Coordinate a = new Coordinate(coord.r - 1, coord.c + 1);
                    final Word word = new Word(coord, a, new Coordinate(coord.r - 2, coord.c + 2));
                    word.a = a;
                    X_WORDS.add(word);
                }
            } else if (ch == 'A') {
                if (wS[coord.r + 1][coord.c - 1] == 'M' && wS[coord.r - 1][coord.c + 1] == 'S') {
                    final Word word = new Word(new Coordinate(coord.r + 1, coord.c - 1), coord, new Coordinate(coord.r - 1, coord.c + 1));
                    word.a = coord;
                    X_WORDS.add(word);
                }
            } else if (ch == 'S') {
                if (wS[coord.r + 2][coord.c - 2] == 'M' && wS[coord.r + 1][coord.c - 1] == 'A') {
                    final Coordinate a = new Coordinate(coord.r + 1, coord.c - 1);
                    final Word word = new Word(new Coordinate(coord.r + 2, coord.c - 2), a, coord);
                    word.a = a;
                    X_WORDS.add(word);
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagUpBwdMas(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'M') {
                if (wS[coord.r - 1][coord.c - 1] == 'A' && wS[coord.r - 2][coord.c - 2] == 'S') {
                    final Coordinate a = new Coordinate(coord.r - 1, coord.c - 1);
                    final Word word = new Word(coord, a, new Coordinate(coord.r - 2, coord.c - 2));
                    word.a = a;
                    X_WORDS.add(word);
                }
            } else if (ch == 'A') {
                if (wS[coord.r + 1][coord.c + 1] == 'M' && wS[coord.r - 1][coord.c - 1] == 'S') {
                    final Word word = new Word(new Coordinate(coord.r + 1, coord.c + 1), coord, new Coordinate(coord.r - 1, coord.c - 1));
                    word.a = coord;
                    X_WORDS.add(word);
                }
            } else if (ch == 'S') {
                if (wS[coord.r + 2][coord.c + 2] == 'M' && wS[coord.r + 1][coord.c + 1] == 'A') {
                    final Coordinate a = new Coordinate(coord.r + 1, coord.c + 1);
                    final Word word = new Word(new Coordinate(coord.r + 2, coord.c + 2), a, coord);
                    word.a = a;
                    X_WORDS.add(word);
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void isHitDiagDnBwdMas(final char[][] wS, final char ch, final Coordinate coord) {
        try {
            if (ch == 'M') {
                if (wS[coord.r + 1][coord.c - 1] == 'A' && wS[coord.r + 2][coord.c - 2] == 'S') {
                    final Coordinate a = new Coordinate(coord.r + 1, coord.c - 1);
                    final Word word = new Word(coord, a, new Coordinate(coord.r + 2, coord.c - 2));
                    word.a = a;
                    X_WORDS.add(word);
                }
            } else if (ch == 'A') {
                if (wS[coord.r - 1][coord.c + 1] == 'M' && wS[coord.r + 1][coord.c - 1] == 'S') {
                    final Word word = new Word(new Coordinate(coord.r - 1, coord.c + 1), coord, new Coordinate(coord.r + 1, coord.c - 1));
                    word.a = coord;
                    X_WORDS.add(word);
                }
            } else if (ch == 'S') {
                if (wS[coord.r - 2][coord.c + 2] == 'M' && wS[coord.r - 1][coord.c + 1] == 'A') {
                    final Coordinate a = new Coordinate(coord.r - 1, coord.c + 1);
                    final Word word = new Word(new Coordinate(coord.r - 2, coord.c + 2), a, coord);
                    word.a = a;
                    X_WORDS.add(word);
                }
            }
        } catch (final ArrayIndexOutOfBoundsException e) {
            // move on
        }
    }

    private static void printResults(final Set<Word> words, final char[][] ws) {
        for (final Word word : words) {
            for (final Coordinate coordinate : word.coordinates) {
                System.out.printf("%c", ws[coordinate.r][coordinate.c]);
            }
            System.out.println();
        }
    }

    private static class Coordinate {

        private final int r;
        private final int c;

        public Coordinate(final int r, final int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            final Coordinate that = (Coordinate) o;
            return r == that.r && c == that.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }
    }

    private static class Word {

        final Set<Coordinate> coordinates;
        Coordinate a;

        public Word(final Coordinate first, final Coordinate second, final Coordinate third) {
            final Set<Coordinate> coords = new HashSet<>();
            coords.add(first);
            coords.add(second);
            coords.add(third);
            this.coordinates = Collections.unmodifiableSet(coords);
        }

        public Word(final Coordinate first, final Coordinate second, final Coordinate third, final Coordinate fourth) {
            final Set<Coordinate> coords = new HashSet<>();
            coords.add(first);
            coords.add(second);
            coords.add(third);
            coords.add(fourth);
            this.coordinates = Collections.unmodifiableSet(coords);
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            final Word word = (Word) o;
            return Objects.equals(coordinates, word.coordinates);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(coordinates);
        }
    }
}
