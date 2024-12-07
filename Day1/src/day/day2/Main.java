package day.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("ADVENT OF CODE 2024 - DAY 2");
//        System.out.println("PART 1");
//        System.out.println("Result of part 1: " + processPart1Data());
//        System.out.println("PART 2");
//        System.out.println("Result of part 2: " + processPart2Data());
        System.out.println("Result of part 2: " + process());
//        var list = new LinkedList<Integer>();
//        list.add(1);
//        list.add(3);
//        list.add(2);
//        list.add(4);
//        list.add(5);
//        list.remove(0);
//        System.out.println(test(list, 1));
    }

    private static Integer processPart1Data() {
        // Specify the file path
        String filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day2/input-test.txt";

        try {
            var loadData = Files.readAllLines(Path.of(filePath)).stream()
                    .map(line -> Arrays.stream(line.split(" "))
                            .map(Integer::parseInt)
                            .toList()
                    ).toList();

            var result = 0;
            for (var singleList : loadData) {
                var isSafe = true;
                var isAscending = singleList.get(1) > singleList.get(0);
                for (int i = 0; i < singleList.size() - 1; i++) {
                    var current = singleList.get(i);
                    var next = singleList.get(i + 1);

                    if (isUnsafe(current, next, isAscending)) {
                        isSafe = false;
                        break;
                    }
                }

                if (isSafe) {
                    result++;
                }
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer processPart2Data() {
        // Specify the file path
        String filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day2/input-test.txt";

        try {
            var loadData = Files.readAllLines(Path.of(filePath)).stream()
                    .map(line -> Arrays.stream(line.split(" "))
                            .map(Integer::parseInt)
                            .toList()
                    ).toList();

            var result = 0;
            var lineCounter = 1;
            for (var singleList : loadData) {
                var maxOneCompleted = false;
                var isSafe = true;
                var isAscending = singleList.get(1) > singleList.get(0);
                for (int i = 0; i < singleList.size() - 1; i++) {
                    var current = singleList.get(i);
                    var next = singleList.get(i + 1);

                    if (isUnsafe(current, next, isAscending)) {
                        var left = test(singleList, i);
                        var right = test(singleList, i + 1);
                        if (maxOneCompleted || (!left && !right)) {
                            isSafe = false;
                            break;
                        }
                        maxOneCompleted = true;
                    }
                }

                if (isSafe) {
                    result++;
                } else {
                    System.out.println(singleList);
                }
//                System.out.println("Processing line: " + lineCounter++ + " Is safe: " + isSafe + "\n");
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer process() {
        // Specify the file path
        String filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day2/input-task.txt";

        try {
            var loadData = Files.readAllLines(Path.of(filePath)).stream()
                    .map(line -> Arrays.stream(line.split(" "))
                            .map(Integer::parseInt)
                            .toList()
                    ).toList();

            var result = 0;
            for (var el : loadData) {
                List<Integer> singleList = new ArrayList<>(List.copyOf(el));
                var isSafe = true;
                var isAscending0 = singleList.get(1) > singleList.get(0);
                var isAscending2 = singleList.get(2) > singleList.get(1);
                var isAscending3 = singleList.get(3) > singleList.get(2);

                var isAscending = singleList.get(1) > singleList.get(0);
                if (isAscending0 != isAscending2) {
                    isAscending = isAscending0 == isAscending3 ? isAscending : isAscending2;
                }

                var indexToDelete = -1;
                for (int i = 0; i < singleList.size() - 1; i++) {
                    var current = singleList.get(i);
                    var next = singleList.get(i + 1);

                    if (isUnsafe(current, next, isAscending)) {
                        var left = test(singleList, i);
                        var right = test(singleList, i + 1);
                        if (left) {
                            singleList.remove((int) i);
                        } else if (right) {
                            singleList.remove((int) i + 1);
                        } else {
                            isSafe = false;
                        }
                        break;
                    }
                }

                if (isSafe && indexToDelete >= 0) {
                    for (int i = 0; i < singleList.size() - 1; i++) {
                        var current = singleList.get(i);
                        var next = singleList.get(i + 1);

                        if (isUnsafe(current, next, isAscending)) {
                            isSafe = false;
                            break;
                        }
                    }
                }

                if (isSafe) {
                    result++;
                } else {
                    System.out.println(singleList);
                }
//                System.out.println("Processing line: " + lineCounter++ + " Is safe: " + isSafe + "\n");
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean test(List<Integer> singleList, Integer skipIndex) {
        try {
            var isSafe = true;
            List<Integer> copyList = new ArrayList<>(List.copyOf(singleList));
            copyList.remove((int) skipIndex);

            var isAscending = copyList.get(1) > copyList.get(0);
            for (int i = 0; i < copyList.size() - 1; i++) {
                var current = copyList.get(i);
                var next = copyList.get(i + 1);

                if (isUnsafe(current, next, isAscending)) {
                    isSafe = false;
                    break;
                }
            }

            return isSafe;
        } catch (Exception e) {
            System.out.println(singleList);
            return false;
        }
    }

    private static boolean isUnsafe(Integer current, Integer next, Boolean isAscending) {
        if (isAscending) {
            return next <= current || next - current > 3;
        } else {
            return next >= current || current - next > 3;
        }
    }
}