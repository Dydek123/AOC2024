package day.day13;

import day.Day;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class Day13 extends Day {

    private static final Pattern GET_VALUES_PATTERN = Pattern.compile("X[+=](\\d+), Y[+=](\\d+)");
    private static final BigDecimal PRIZE_INCREMENT = new BigDecimal(10000000000000L);
    public static final int MULTIPLIER_X_PRESSES = 3;

    public Day13() {
        setDayName("13");
    }

    @Override
    public Long taskOne(String filePath) {
        try {
            return calculatePresses(filePath, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long taskTwo(String filePath) {
        try {
            return calculatePresses(filePath, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private long calculatePresses(String filePath, boolean increasePrize) throws IOException {
        var result = 0L;
        var xQuery = getArrayWithDefaultValues();
        var yQuery = getArrayWithDefaultValues();
        var lines = Files.readAllLines(Path.of(filePath));
        for (int i = 0; i < lines.size(); i++) {
            var query = getPairValues(lines.get(i));
            switch (i % 4) {
                case 0:
                    xQuery[0] = query[0];
                    yQuery[0] = query[1];
                    break;
                case 1:
                    xQuery[1] = query[0];
                    yQuery[1] = query[1];
                    break;
                case 2:
                    if (increasePrize) {
                        xQuery[2] = query[0].add(PRIZE_INCREMENT);
                        yQuery[2] = query[1].add(PRIZE_INCREMENT);
                    } else {
                        xQuery[2] = query[0];
                        yQuery[2] = query[1];
                    }
                    result += resolveEquation(xQuery, yQuery);
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    private BigDecimal[] getArrayWithDefaultValues() {
        return new BigDecimal[]{new BigDecimal(0), new BigDecimal(0), new BigDecimal(0)};
    }

    private Long resolveEquation(BigDecimal[] xQuery, BigDecimal[] yQuery) {
        var det = xQuery[0].multiply(yQuery[1]).subtract(xQuery[1].multiply(yQuery[0]));

        if (det.compareTo(BigDecimal.ZERO) == 0) {
            return 0L;
        }

        var xNumerator = xQuery[2].multiply(yQuery[1]).subtract(yQuery[2].multiply(xQuery[1]));
        var yNumerator = xQuery[0].multiply(yQuery[2]).subtract(yQuery[0].multiply(xQuery[2]));
        if (isDecimalSolution(xNumerator, det, yNumerator)) {
            return 0L;
        }
        var x = (xNumerator).divide(det, RoundingMode.HALF_UP);
        var y = yNumerator.divide(det, RoundingMode.HALF_UP);
        return Long.parseLong(x.multiply(new BigDecimal(MULTIPLIER_X_PRESSES)).add(y).toString());
    }

    private boolean isDecimalSolution(BigDecimal xNumerator, BigDecimal det, BigDecimal yNumerator) {
        return xNumerator.remainder(det).compareTo(BigDecimal.ZERO) != 0 || yNumerator.remainder(det).compareTo(BigDecimal.ZERO) != 0;
    }


    private BigDecimal[] getPairValues(String line) {
        var matcher = GET_VALUES_PATTERN.matcher(line);
        if (matcher.find()) {
            return new BigDecimal[]{new BigDecimal(matcher.group(1)), new BigDecimal(matcher.group(2))};
        }
        return new BigDecimal[]{new BigDecimal(0), new BigDecimal(0)};
    }
}
//TODO ADD TESTS