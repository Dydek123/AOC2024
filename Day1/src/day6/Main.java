package day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Advent Of Code 2024 - Day 6");
        long startTime = System.currentTimeMillis();
        var task1Result = task1("/home/vboxuser/Desktop/AOC2024/Day1/src/day6/input-task.txt");
        long endTime = System.currentTimeMillis();
        System.out.println("Task 1: " + task1Result + " ExecutionTime: " + (endTime - startTime) + "ms");
    }

    public static int task1(String filePath) {
        var grid = readGridFromFile(filePath);
        System.out.println("Initial grid");
//        printGrid(grid);
        var startPosition = findStartPosition(grid);
//        System.out.println("Starting at: " + Arrays.toString(startPosition));
        Integer[] direction = {-1, 0};

        while (true) {
            try {
                makeMove(grid, startPosition, direction);
//                printGrid(grid);
//                System.out.println("Starting at: " + Arrays.toString(startPosition));
            } catch (IndexOutOfBoundsException e) {
//                System.out.println("Guard leaves the field");
                break;
            }
        }


        return countVisitedPlaces(grid);
    }

    public static int task2(String filePath) {
        var grid = readGridFromFile(filePath);
        System.out.println("Initial grid");
        printGrid(grid);
        var startPosition = findStartPosition(grid);
        System.out.println("Starting at: " + Arrays.toString(startPosition));
        Integer[] direction = {-1, 0};

        var maxSteps = 0;
        while (true) {
            try {
                maxSteps++;
                makeMove2(grid, startPosition, direction);
                printGrid(grid);
                System.out.println("Starting at: " + Arrays.toString(startPosition));

                if (maxSteps > 100) {
                    System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!");
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
//                System.out.println("Guard leaves the field");
                break;
            }
        }


        return countVisitedPlaces(grid);
    }

    private static void makeMove(char[][] grid, Integer[] startPosition, Integer[] direction) {
        if (grid[startPosition[0] + direction[0]][startPosition[1] + direction[1]] == '#') {
            if (direction[0] == 1 || direction[0] == -1) {
                direction[1] = direction[0] * (-1);
                direction[0] = 0;
            } else {
                direction[0] = direction[1];
                direction[1] = 0;
            }
        }
        startPosition[0] = startPosition[0] + direction[0];
        startPosition[1] = startPosition[1] + direction[1];

        grid[startPosition[0]][startPosition[1]] = '^';
    }

    private static void makeMove2(char[][] grid, Integer[] startPosition, Integer[] direction) {
        if (grid[startPosition[0] + direction[0]][startPosition[1] + direction[1]] == '#') {
            if (direction[0] == 1 || direction[0] == -1) {
                direction[1] = direction[0] * (-1);
                direction[0] = 0;
            } else {
                direction[0] = direction[1];
                direction[1] = 0;
            }
            grid[startPosition[0]][startPosition[1]] = '+';
        }

        startPosition[0] = startPosition[0] + direction[0];
        startPosition[1] = startPosition[1] + direction[1];
        if (grid[startPosition[0]][startPosition[1]] == '^') {
            return;
        }

        if (direction[0] == 0 && grid[startPosition[0] + direction[0]][startPosition[1] + direction[1]] == '|') {
            System.out.println("FOUND OBSTACLE !!!!");
        } if (direction[0] == 0 && grid[startPosition[0] + direction[0]][startPosition[1] + direction[1]] == '-') {
            System.out.println("FOUND OBSTACLE !!!!");
        }


        if (direction[0] == 0) {
            if (grid[startPosition[0]][startPosition[1]] == '|') {
                grid[startPosition[0]][startPosition[1]] = '+';
            } else {
                grid[startPosition[0]][startPosition[1]] = '-';
            }
        } else {
            if (grid[startPosition[0]][startPosition[1]] == '-') {
                grid[startPosition[0]][startPosition[1]] = '+';
            } else {
                grid[startPosition[0]][startPosition[1]] = '|';
            }
        }

    }


    //TODO extract to Utils
    public static char[][] readGridFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }

        int rows = lines.size();
        if (rows == 0) return null;

        int cols = lines.getFirst().length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        return grid;
    }

    public static void printGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    private static Integer[] findStartPosition(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '^') {
                    Integer[] startPosition = new Integer[2];
                    startPosition[0] = i;
                    startPosition[1] = j;
                    return startPosition;
                }
            }
        }
        throw new IllegalArgumentException("Start position not found");
    }

    private static int countVisitedPlaces(char[][] grid) {
        var result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '^') {
                    result++;
                }
            }
        }
        return result;
    }
}
