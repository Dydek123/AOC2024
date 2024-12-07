package day.day7;

import day.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day7 extends Day {
    @Override
    public Long taskOne(String filePath) {

        return verifyCalculation(filePath, new char[]{'+', '*'});
    }

    @Override
    public Long taskTwo(String filePath) {

        return verifyCalculation(filePath, new char[]{'+', '*', '|'});
    }

    private static long verifyCalculation(String filePath, char[] x) {
        try {
            var correctData = 0L;
            var allLines = Files.readAllLines(Paths.get(filePath));
            for (var data : allLines) {
                var splitted = data.split(": ");
                var expectedResult = Long.parseLong(splitted[0]);
                var operations = Arrays.stream(splitted[1].split(" "))
                        .map(Long::parseLong)
                        .toArray(Long[]::new);

                var permutations = generatePermutations(operations.length - 1, x);
                correctData = processSingleValidation(permutations, operations, expectedResult, correctData);
            }
            return correctData;
        } catch (IOException e) {
            System.out.println("Cannot read file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static long processSingleValidation(char[][] permutations, Long[] operations, long expectedResult, long correctData) {
        for (char[] permutation : permutations) {
            var actualResult = operations[0];
            for (int j = 0; j < permutation.length; j++) {
                var nextNumber = operations[j + 1];
                actualResult = doOperation(permutation[j], actualResult, nextNumber);
            }
            if (actualResult == expectedResult) {
                correctData += expectedResult;
                break;
            }
        }
        return correctData;
    }

    private static Long doOperation(char operation, Long actualResult, Long nextNumber) {
        switch (operation) {
            case '+':
                actualResult += nextNumber;
                break;
            case '*':
                actualResult *= nextNumber;
                break;
            case '|':
                actualResult = Long.parseLong(String.valueOf(actualResult) + nextNumber);
                break;
            default:
                System.out.println("Invalid operation: " + operation);
        }
        return actualResult;
    }

    public static char[][] generatePermutations(int n, char[] operators) {
        var operatorsCount = operators.length;
        if (operatorsCount < 2) {
            throw new IllegalArgumentException("Number of operators (k) must be at least 2");
        }

        int totalPermutations = (int) Math.pow(operatorsCount, n);
        char[][] result = new char[totalPermutations][n];

        for (int i = 0; i < totalPermutations; i++) {
            int current = i;
            for (int j = 0; j < n; j++) {
                int operatorIndex = current % operatorsCount;
                result[i][j] = operators[operatorIndex];
                current /= operatorsCount;
            }
        }

        return result;
    }
}
