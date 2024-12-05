package day4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
    }

    private static void task2() {
        String filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day4/input-task.txt";
        char[][] grid = readGridFromFile(filePath);
        var result = 0;
        if (grid != null) {
            for (int i = 0; i < grid.length - 2; i++) {
                for (int j = 0; j < grid[i].length - 2; j++) {
                    if (grid[i][j] == 'M' && grid[i + 1][j + 1] == 'A' && grid[i + 2][j + 2] == 'S') {
                        if (grid[i + 2][j] == 'M' && grid[i + 1][j + 1] == 'A' && grid[i][j + 2] == 'S') {
                            result++;
                        }
                        if (grid[i + 2][j] == 'S' && grid[i + 1][j + 1] == 'A' && grid[i][j + 2] == 'M') {
                            result++;
                        }
                    }
                    if (grid[i][j] == 'S' && grid[i + 1][j + 1] == 'A' && grid[i + 2][j + 2] == 'M') {
                        if (grid[i + 2][j] == 'M' && grid[i + 1][j + 1] == 'A' && grid[i][j + 2] == 'S') {
                            result++;
                        }
                        if (grid[i + 2][j] == 'S' && grid[i + 1][j + 1] == 'A' && grid[i][j + 2] == 'M') {
                            result++;
                        }
                    }
                }
//                System.out.println();
            }
        }
        System.out.println(result);
    }

    private static void task1() {
        String filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day4/input-task.txt";
        char[][] grid = readGridFromFile(filePath);

        if (grid != null) {
            List<String> combinations = getFourLetterCombinations(grid);

            long count = combinations.stream()
                    .filter(combination -> "XMAS".equals(combination) || "SAMX".equals(combination))
                    .count();

            System.out.println(count);
        }
    }

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

        // Convert the lines into a char[][] grid
        int rows = lines.size();
        if (rows == 0) return null;

        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        return grid;
    }

    public static List<String> getFourLetterCombinations(char[][] grid) {
        List<String> combinations = new ArrayList<>();

        int rows = grid.length;
        int cols = grid[0].length;

        // Horizontal combinations (row-wise)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j <= cols - 4; j++) {
                combinations.add(new String(grid[i], j, 4));
            }
        }

        // Vertical combinations (column-wise)
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i <= rows - 4; i++) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < 4; k++) {
                    sb.append(grid[i + k][j]);
                }
                combinations.add(sb.toString());
            }
        }

        // Diagonal combinations (top-left to bottom-right)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 0; j <= cols - 4; j++) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < 4; k++) {
                    sb.append(grid[i + k][j + k]);
                }
                combinations.add(sb.toString());
            }
        }

        // Diagonal combinations (top-right to bottom-left)
        for (int i = 0; i <= rows - 4; i++) {
            for (int j = 3; j < cols; j++) {
                StringBuilder sb = new StringBuilder();
                for (int k = 0; k < 4; k++) {
                    sb.append(grid[i + k][j - k]);
                }
                combinations.add(sb.toString());
            }
        }

        return combinations;
    }
}
