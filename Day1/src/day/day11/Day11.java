package day.day11;

import day.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day11 extends Day {

    public Day11() {
        setDayName("11");
    }

    @Override
    public Long taskOne(String filePath) {
        var maxDepth = 25;
        try {
            var data = getDataInput(filePath);
            return calculateStonesToMaxDepth(data, maxDepth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long taskTwo(String filePath) {
        var maxDepth = 75;
        try {
            var data = getDataInput(filePath);
            return calculateStonesToMaxDepth(data, maxDepth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long[] getDataInput(String filePath) throws IOException {
        return Arrays.stream(Files.readString(Paths.get(filePath)).split(" "))
                .flatMapToLong(num -> LongStream.of(Long.parseLong(num)))
                .toArray();
    }

    private Long calculateStonesToMaxDepth(long[] initialNumbers, int maxDepth) {
        // Map to store the current count of each unique number
        var currentCounts = new HashMap<Long, Long>();

        // Initialize with the starting stones
        for (long num : initialNumbers) {
            currentCounts.put(num, currentCounts.getOrDefault(num, 0L) + 1);
        }

        for (int depth = 0; depth < maxDepth; depth++) {
            var nextCounts = new HashMap<Long, Long>();

            for (Map.Entry<Long, Long> entry : currentCounts.entrySet()) {
                var number = entry.getKey();
                var count = entry.getValue();

                if (number == 0) {
                    // Rule 1: Replace 0 with 1
                    nextCounts.put(1L, nextCounts.getOrDefault(1L, 0L) + count);
                } else if (String.valueOf(number).length() % 2 == 0) {
                    // Rule 2: Split even-length numbers
                    var pow = (long) Math.pow(10, String.valueOf(number).length() / 2);
                    var leftPart = number / pow;
                    var rightPart = number % pow;
                    nextCounts.put(leftPart, nextCounts.getOrDefault(leftPart, 0L) + count);
                    nextCounts.put(rightPart, nextCounts.getOrDefault(rightPart, 0L) + count);
                } else {
                    // Rule 3: Multiply odd-length numbers by 2024
                    var newNumber = number * 2024L;
                    nextCounts.put(newNumber, nextCounts.getOrDefault(newNumber, 0L) + count);
                }
            }

            currentCounts = nextCounts;
        }

        return currentCounts.values().stream().reduce(0L, Long::sum);
    }
}

//TODO add Tests