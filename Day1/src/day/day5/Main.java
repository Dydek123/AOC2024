package day.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        task1();
        task2();
    }

    private static void task1() {
        String filePathPages = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day5/input-task-pages.txt";
        String filePathUpdates = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day5/input-task-updates.txt";
        List<List<Integer>> pages = new ArrayList<>();

        try {
            var loadPages = Files.readAllLines(Paths.get(filePathPages));
            loadPages.forEach(line -> {
                var splitted = line.split("\\|");
                var listElement = new ArrayList<Integer>();
                listElement.add(Integer.parseInt(splitted[0]));
                listElement.add(Integer.parseInt(splitted[1]));
                pages.add(listElement);
            });
        } catch (IOException e) {
            System.out.println("Cannot load pages " + e.getMessage());
        }

        try {
            var result = 0;
            var loadPages = Files.readAllLines(Paths.get(filePathUpdates));
            for (String line : loadPages) {
                var splitted = line.split(",");
                var isValid = true;
                for (int i = 0; i < splitted.length - 1; i++) {
                    var processingNumber = Integer.parseInt(splitted[i]);
                    var availableNextNumbers = pages.stream()
                            .filter(el -> el.getFirst() == processingNumber)
                            .map(el -> el.get(1))
                            .toList();

                    for (int j = i + 1; j < splitted.length; j++) {
                        if (!availableNextNumbers.contains(Integer.parseInt(splitted[j]))) {
                            isValid = false;
                            break;
                        }
                    }

                    if (!isValid) {
                        break;
                    }
                }
                if (isValid) {
                    var middleNumber = Integer.parseInt(splitted[splitted.length / 2]);
//                    System.out.println(middleNumber);
                    result += middleNumber;
                }
            }
//            System.out.println(pages);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("Cannot load pages " + e.getMessage());
        }

    }

    private static void task2() {
        String filePathPages = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day5/input-task-pages.txt";
        String filePathUpdates = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day5/input-task-updates.txt";
        List<List<Integer>> pages = new ArrayList<>();

        try {
            var loadPages = Files.readAllLines(Paths.get(filePathPages));
            loadPages.forEach(line -> {
                var splitted = line.split("\\|");
                var listElement = new ArrayList<Integer>();
                listElement.add(Integer.parseInt(splitted[0]));
                listElement.add(Integer.parseInt(splitted[1]));
                pages.add(listElement);
            });
        } catch (IOException e) {
            System.out.println("Cannot load pages " + e.getMessage());
        }

        try {
            var result = 0;
            var loadPages = Files.readAllLines(Paths.get(filePathUpdates));
            for (String line : loadPages) {
                var splitted = Arrays.stream(line.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
                var isValid = true;
                for (int i = 0; i < splitted.length - 1; i++) {
                    var processingNumber = splitted[i];
                    var availableNextNumbers = pages.stream()
                            .filter(el -> el.getFirst().equals(processingNumber))
                            .map(el -> el.get(1))
                            .toList();

                    for (int j = i + 1; j < splitted.length; j++) {
                        if (!availableNextNumbers.contains(splitted[j])) {
                            isValid = false;
                            break;
                        }
                    }

                    if (!isValid) {
                        break;
                    }
                }
                if (!isValid) {
                    System.out.println(line);
                    result += process(splitted, pages);
                }
            }
//            System.out.println(pages);
            System.out.println("RESULT: " + result);
        } catch (IOException e) {
            System.out.println("Cannot load pages " + e.getMessage());
        }

    }

    private static Integer process(Integer[] splitted, List<List<Integer>> pages) {
        for (int i = 0; i < splitted.length - 1; i++) {
            var processingNumber = splitted[i];
            var availableNextNumbers = pages.stream()
                    .filter(el -> el.getFirst().equals(processingNumber))
                    .map(el -> el.get(1))
                    .toList();
            for (int j = i + 1; j < splitted.length; j++) {
                if (!availableNextNumbers.contains(splitted[j])) {
                    System.out.println(processingNumber);
                    var swapResult = swap(splitted, i, j);
                    System.out.println(Arrays.toString(swapResult));
                    System.out.println(validateArray(swapResult, i, pages, splitted[j]));

                    var isValid = false;
                    for (int k = j; k < splitted.length; k++) {
                        if (validateArray(swapResult, i, pages, splitted[k])) {
                            splitted = swapResult;
                            isValid = true;
                            break;
                        }
                    }
                    if (!isValid) {
                        System.out.println("NO OPTION TO PROCESS THIS LINE");
                    }
                }
            }
        }
        System.out.println("RESULT OF THIS LINE: " + Arrays.toString(splitted));
        return splitted[splitted.length/2];
    }

    private static boolean validateArray(Integer[] splitted, int i, List<List<Integer>> pages, int processingNumber) {
        var availableNextNumbers = pages.stream()
                .filter(el -> el.getFirst().equals(processingNumber))
                .map(el -> el.get(1))
                .toList();
        for (int j = i + 1; j < splitted.length; j++) {
            if (!availableNextNumbers.contains(splitted[j])) {
                return false;
            }
        }
        return true;
    }

    private static Integer[] swap(Integer[] list, int first, int second) {
        var copy = Arrays.copyOf(list, list.length);
        var tmp = copy[first];
        copy[first] = copy[second];
        copy[second] = tmp;
        return copy;
    }
}
