package day.day8;

import day.Day;
import day.DayUtils;

import java.util.*;

public class Day8 extends Day {

    @Override
    public Long taskOne(String filePath) {
        Set<Long> uniquePositions = new HashSet<>();
        var grid = DayUtils.readGridFromFile(filePath);

        var occurenciesMap = getOccurenciesMap(grid);


        for (var entry : occurenciesMap.entrySet()) {
            for (int i = 0; i < entry.getValue().size() - 1; i++) {
                for (int j = i + 1; j < entry.getValue().size(); j++) {
                    setSingleAntinode(new int[]{entry.getValue().get(i).get(0), entry.getValue().get(i).get(1)}, new int[]{entry.getValue().get(j).get(0), entry.getValue().get(j).get(1)}, grid, uniquePositions);
                }
            }
        }
        return (long) uniquePositions.size();
    }

    private static HashMap<String, List<List<Integer>>> getOccurenciesMap(char[][] grid) {
        var occurenciesMap = new HashMap<String, List<List<Integer>>>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != '.') {
                    occurenciesMap.putIfAbsent(String.valueOf(grid[i][j]), new ArrayList<>());
                    occurenciesMap.get(String.valueOf(grid[i][j])).add(List.of(i, j));
                }
            }
        }
        return occurenciesMap;
    }

    @Override
    public Long taskTwo(String filePath) {
        Set<Long> uniquePositions = new HashSet<>();
        var grid = DayUtils.readGridFromFile(filePath);

        var occurenciesMap = getOccurenciesMap(grid);

        for (var entry : occurenciesMap.entrySet()) {
            if (entry.getValue().size() > 1) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    uniquePositions.add(entry.getValue().get(i).get(0) * 1000L + entry.getValue().get(i).get(1));
                }
            }
            for (int i = 0; i < entry.getValue().size() - 1; i++) {
                for (int j = i + 1; j < entry.getValue().size(); j++) {
                    setAntinodeInLoop(new int[]{entry.getValue().get(i).get(0), entry.getValue().get(i).get(1)}, new int[]{entry.getValue().get(j).get(0), entry.getValue().get(j).get(1)}, grid, uniquePositions);
                }
            }
        }
        return (long) uniquePositions.size();
    }

    private static void setSingleAntinode(int[] start, int[] end, char[][] grid, Set<Long> uniquePositions) {
        int[] distance = calculateDistance(start, end);
        try {
            searchSingleAntinode(end, grid, uniquePositions, distance);
            var negatedDistance = new int[]{-distance[0], -distance[1]};
            searchSingleAntinode(start, grid, uniquePositions, negatedDistance);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unexpected out of bounds exception");
        }
    }

    private static void searchSingleAntinode(int[] end, char[][] grid, Set<Long> uniquePositions, int[] distance) {
        if (end[0] + distance[0] < grid.length && end[0] + distance[0] >= 0 && end[1] + distance[1] < grid.length && end[1] + distance[1] >= 0) {
            long a = (end[0] + distance[0]) * 1000L;
            long b = end[1] + distance[1];
            uniquePositions.add(a + b);
        }
    }

    private static void setAntinodeInLoop(int[] start, int[] end, char[][] grid, Set<Long> uniquePositions) {
        int[] distance = calculateDistance(start, end);
        try {
            searchAntinodes(end, grid, uniquePositions, distance);
            var negatedDistance = new int[]{-distance[0], -distance[1]};
            searchAntinodes(start, grid, uniquePositions, negatedDistance);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Unexpected out of bounds exception");
        }
    }

    private static void searchAntinodes(int[] startPosition, char[][] grid, Set<Long> uniquePositions, int[] distance) {
        var moveX = distance[0];
        var moveY = distance[1];
        while (startPosition[0] + moveX < grid.length && startPosition[0] + moveX >= 0 && startPosition[1] + moveY < grid.length && startPosition[1] + moveY >= 0) {
            long a = (startPosition[0] + moveX) * 1000L;
            long b = startPosition[1] + moveY;
            uniquePositions.add(a + b);

            moveX += distance[0];
            moveY += distance[1];
        }
    }

    private static int[] calculateDistance(int[] start, int[] end) {
        return new int[]{end[0] - start[0], end[1] - start[1]};
    }
}
