package day.day7;

import day.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day7 extends Day {
    @Override
    public Long taskOne(String filePath) {

        try {
            var correctData = 0L;
            var allLines = Files.readAllLines(Paths.get(filePath));
            for (var data : allLines) {
                var splitted = data.split(": ");
                var expectedResult = Long.parseLong(splitted[0]);
                var operations = Arrays.stream(splitted[1].split(" "))
                        .map(Long::parseLong)
                        .toArray(Long[]::new);

                var permutations = generatePermutations(operations.length - 1);

                for (int i = 0; i < permutations.length; i++) {
                    var actualResult = operations[0];
                    for (int j = 0; j < permutations[i].length; j++) {
                        var nextNumber = operations[j + 1];
                        if (permutations[i][j] == '+') {
                            actualResult += nextNumber;
                        }
                        if (permutations[i][j] == '*') {
                            actualResult *= nextNumber;
                        }
                    }
                    if (actualResult == expectedResult) {
                        correctData += expectedResult;
                        break;
                    }
                }
            }
            return correctData;
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long taskTwo(String filePath) {
        return 0L;
    }

    public static char[][] generatePermutations(int n) {
        // Total number of permutations is 2^n
        int totalPermutations = (int) Math.pow(2, n);
        char[][] result = new char[totalPermutations][n];  // 2^n permutations, each of length n

        // Generate permutations
        for (int i = 0; i < totalPermutations; i++) {
            for (int j = 0; j < n; j++) {
                // Using bitwise operation to decide whether it's '+' or '*'
                if ((i & (1 << (n - j - 1))) != 0) {
                    result[i][j] = '*';
                } else {
                    result[i][j] = '+';
                }
            }
        }

        return result;
    }
}
