package com.broomington.twentytwentyfour;

import com.broomington.util.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.broomington.util.FileReader.getInput;

public class Day2 {

    public static void main(final String[] args) throws IOException {
        final String input = getInput(2024, Day2.class);

        System.out.println(countSafeReports(input, Part.ONE));
        System.out.println(countSafeReports(input, Part.TWO));
    }

    private static int countSafeReports(final String input, final Part part) {
        return Math.toIntExact(Arrays.stream(input.split("\n"))
                .map(report -> report.split("( )+"))
                .map(report -> Arrays.stream(report)
                        .map(Integer::parseInt)
                        .toList())
                .map(report -> isValid(report, part))
                .filter(validity -> validity.equals(true))
                .count());
    }

    private static boolean isValid(final List<Integer> report, final Part part) {
        final ReportType type = report.get(1) > report.get(0) ? ReportType.INCREASING : ReportType.DECREASING;
        for (int i = 1; i < report.size(); i++) {
            final int current = report.get(i);
            final int previous = report.get(i - 1);
            final int diff = type == ReportType.INCREASING ? current - previous : previous - current;
            if (type == ReportType.INCREASING && current <= previous || type == ReportType.DECREASING && previous <= current || !(diff >= 1 && diff <= 3)) {
                if (part == Part.ONE) {
                    return false;
                } else {
                    return isValidWithTolerance(report);
                }
            }
        }
        return true;
    }

    private static boolean isValidWithTolerance(final List<Integer> report) {
        return IntStream.range(0, report.size())
                .mapToObj(i -> {
                    final List<Integer> revisedReport = new ArrayList<>(report);
                    revisedReport.remove(i);
                    return revisedReport;
                })
                .map(revisedReport -> isValid(revisedReport, Part.ONE))
                .anyMatch(validity -> validity.equals(true));
    }

    private enum ReportType {
        INCREASING,
        DECREASING
    }
}
