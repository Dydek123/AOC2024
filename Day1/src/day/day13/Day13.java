package day.day13;

import day.Day;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Day13 extends Day {
    public Day13() {
        setDayName("13");
    }

    @Override
    public Long taskTwo(String filePath) {
        var result = 0L;
        try {
            var xQuery= new BigDecimal[]{new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)};;
            var yQuery= new BigDecimal[]{new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)};;
            var lines = Files.readAllLines(Path.of(filePath));
            for (int i = 0; i < lines.size(); i++) {
                var query = findPosition(lines.get(i));
                switch (i % 4) {
                    case 0:
                        xQuery[0] = new BigDecimal(query[0]);
                        yQuery[0] = new BigDecimal(query[1]);
                        break;
                    case 1:
                        xQuery[1] = new BigDecimal(query[0]);
                        yQuery[1] = new BigDecimal(query[1]);
                        break;
                    case 2:
                        xQuery[2] = new BigDecimal(query[0] + 10000000000000L);
                        yQuery[2] = new BigDecimal(query[1] + 10000000000000L);
                        result += calculate(xQuery, yQuery);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Long taskOne(String filePath) {
        return 0L;
//        var result = 0L;
//        try {
//            var xQuery = new long[]{0, 0, 0};
//            var yQuery = new long[]{0, 0, 0};
//            var lines = Files.readAllLines(Path.of(filePath));
//            for (int i = 0; i < lines.size(); i++) {
//                var query = findPosition(lines.get(i));
//                switch (i % 4) {
//                    case 0:
//                        xQuery[0] = query[0];
//                        yQuery[0] = query[1];
//                        break;
//                    case 1:
//                        xQuery[1] = query[0];
//                        yQuery[1] = query[1];
//                        break;
//                    case 2:
//                        xQuery[2] = query[0];
//                        yQuery[2] = query[1];
//                        result += calculate(xQuery, yQuery);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return result;
    }

    private long calculate(long[] xQuery, long[] yQuery) {
        // Calculate determinant
        var det = xQuery[0] * yQuery[1] - xQuery[1] * yQuery[0];
        System.out.println("Calculate query: " + Arrays.toString(xQuery) + ", " + Arrays.toString(yQuery) + ", det=" + det);

        if (det == 0) {
            return 0;
        }

        // Use floating-point division to compute x and y
        var x = (double) (xQuery[2] * yQuery[1] - yQuery[2] * xQuery[1]) / det;
        var y = (double) (xQuery[0] * yQuery[2] - yQuery[0] * xQuery[2]) / det;

        // Convert results to long if needed (rounding to nearest integer)
        long xRounded = Math.round(x);
        long yRounded = Math.round(y);

        System.out.println("xRounded=" + xRounded + ", yRounded=" + yRounded + ", x=" + x + ", y=" + y);
        // Compute the result
        if ((double) xRounded == x && (double) yRounded == y && x <= 100 && y <= 100) {
            System.out.println("Result: " + xRounded + "," + yRounded + " = " + 3 * xRounded + yRounded);
            return 3 * xRounded + yRounded;
        }
        System.out.println("No sulution");
        return 0L;
    }

    private Long calculate(BigDecimal[] xQuery, BigDecimal[] yQuery) {
        BigDecimal det = xQuery[0].multiply(yQuery[1]).subtract(xQuery[1].multiply(yQuery[0]));
        System.out.println("Determinant: " + det);

        if (det.compareTo(BigDecimal.ZERO) == 0) {
            return 0L;
        }


        // Calculate x and y
        var xUp = xQuery[2].multiply(yQuery[1]).subtract(yQuery[2].multiply(xQuery[1]));
        var yUp = xQuery[0].multiply(yQuery[2]).subtract(yQuery[0].multiply(xQuery[2]));
        if (xUp.remainder(det).compareTo(BigDecimal.ZERO) != 0 || yUp.remainder(det).compareTo(BigDecimal.ZERO) != 0) {
            return 0L;
        }
        BigDecimal x = (xUp).divide(det, RoundingMode.HALF_UP);
        BigDecimal y = yUp.divide(det, RoundingMode.HALF_UP);

        // Check if x and y are exact integers
        if (x.stripTrailingZeros().scale() > 0 || y.stripTrailingZeros().scale() > 0) {
            // Either x or y is not an integer
            System.out.println("No integer solutions found.");
            return 0L;
        }
        // Print the results for debugging
        System.out.println("x = " + x + ", y = " + y);
        return Long.parseLong(x.multiply(new BigDecimal(3)).add(y).toString());
    }


    private static long[] findPosition(String line) {
        var MULTIPLICATION_PATTERN = Pattern.compile("X[+=](\\d+), Y[+=](\\d+)");
        var matcher = MULTIPLICATION_PATTERN.matcher(line);

        while (matcher.find()) {
            return new long[]{Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2))};
        }
        return new long[]{0, 0};
    }
}
