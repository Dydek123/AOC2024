package day.day8;

import day.Day;
import day.DayUtils;

import java.util.*;

public class Day8 extends Day {

    public static final char EMPTY_FIELD = '.';

    @Override
    public Long taskOne(String filePath) {
        Set<Long> uniquePositions = new HashSet<>();
        char[][] grid = DayUtils.readGridFromFile(filePath);

        var occurrencesMap = buildOccurrencesMap(grid);

        for (var entry : occurrencesMap.values()) {
            addUniquePositions(entry, grid, uniquePositions, false);
        }

        return (long) uniquePositions.size();
    }

    @Override
    public Long taskTwo(String filePath) {
        Set<Long> uniquePositions = new HashSet<>();
        char[][] grid = DayUtils.readGridFromFile(filePath);

        var occurrencesMap = buildOccurrencesMap(grid);

        for (var entry : occurrencesMap.values()) {
            if (entry.size() > 1) {
                for (var pos : entry) {
                    uniquePositions.add(hashPosition(pos));
                }
            }
            addUniquePositions(entry, grid, uniquePositions, true);
        }

        return (long) uniquePositions.size();
    }

    private static Map<Character, List<int[]>> buildOccurrencesMap(char[][] grid) {
        Map<Character, List<int[]>> occurrencesMap = new HashMap<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char ch = grid[i][j];
                if (ch != EMPTY_FIELD) {
                    occurrencesMap.computeIfAbsent(ch, k -> new ArrayList<>()).add(new int[]{i, j});
                }
            }
        }
        return occurrencesMap;
    }

    private static void addUniquePositions(List<int[]> positions, char[][] grid, Set<Long> uniquePositions, boolean includeMultipleOccurrences) {
        for (int i = 0; i < positions.size() - 1; i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                int[] start = positions.get(i);
                int[] end = positions.get(j);
                int[] distance = calculateDistance(start, end);

                addAntinode(start, distance, grid, uniquePositions, includeMultipleOccurrences);
                addAntinode(end, negateDistance(distance), grid, uniquePositions, includeMultipleOccurrences);
            }
        }
    }

    private static void addAntinode(int[] position, int[] distance, char[][] grid, Set<Long> uniquePositions, boolean includeMultipleOccurrences) {
        int x = position[0] + distance[0];
        int y = position[1] + distance[1];

        while (isValidPosition(x, y, grid)) {
            uniquePositions.add(hashPosition(new int[]{x, y}));
            if (!includeMultipleOccurrences) break;

            x += distance[0];
            y += distance[1];
        }
    }

    private static boolean isValidPosition(int x, int y, char[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    private static long hashPosition(int[] position) {
        return position[0] * 1000L + position[1];
    }

    private static int[] calculateDistance(int[] start, int[] end) {
        return new int[]{end[0] - start[0], end[1] - start[1]};
    }

    private static int[] negateDistance(int[] distance) {
        return new int[]{-distance[0], -distance[1]};
    }
}
