package day.day12;

import day.Day;
import day.DayUtils;

import java.util.*;

public class Day12 extends Day {
    int[] dRow = {-1, 0, 1, 0};
    int[] dCol = {0, 1, 0, -1};

    public Day12() {
        setDayName("12");
    }

    @Override
    public Long taskOne(String filePath) {
        var grid = DayUtils.readGridFromFile(filePath);
        System.out.println(Arrays.deepToString(grid));
        var regions = findRegions(grid);
        return calculateCost(regions);
    }

    @Override
    public Long taskTwo(String filePath) {
        return 0L;
    }

    private List<Map.Entry<Character, List<Long>>> findRegions(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<Map.Entry<Character, List<Long>>> regions = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    char plant = grid[i][j];
                    long[] areaPerimeter = exploreRegion(grid, visited, i, j, plant);
                    regions.add(Map.entry(plant, List.of(areaPerimeter[0], areaPerimeter[1])));
                }
            }
        }
        return regions;
    }

    private long[] exploreRegion(char[][] grid, boolean[][] visited, int startRow, int startCol, char plant) {
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        long area = 0;
        long perimeter = 0;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];
            area++;

            // Check all 4 directions
            for (int i = 0; i < 4; i++) {
                int newRow = row + dRow[i];
                int newCol = col + dCol[i];

                if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols || grid[newRow][newCol] != plant) {
                    // Edge of grid or different plant type -> contributes to perimeter
                    perimeter++;
                } else if (!visited[newRow][newCol]) {
                    // Same plant type, not yet visited -> explore
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        return new long[]{area, perimeter};
    }

    private Long calculateCost(List<Map.Entry<Character, List<Long>>> regions) {
        return regions.stream()
                .mapToLong(entry -> entry.getValue().get(0) * entry.getValue().get(1))
                .sum();
    }
}
