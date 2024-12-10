package day.day10;

import day.Day;
import day.DayUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day10 extends Day {
    int[] dRow = {-1, 0, 1, 0};
    int[] dCol = {0, 1, 0, -1};

    public Day10() {
        setDayName("10");
    }

    @Override
    public Long taskOne(String filePath) {
        Map<Long, Set<Long>> trailHeads = new HashMap<>();
        var data = DayUtils.readGridFromFileAsNumbers(filePath);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    countUniqueTrailsToTop(data, 0, i, j, hashPosition(i, j), trailHeads);
                }
            }
        }
        return trailHeads.values().stream().mapToLong(Set::size).sum();
    }

    @Override
    public Long taskTwo(String filePath) {
        var data = DayUtils.readGridFromFileAsNumbers(filePath);
        var result = 0L;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    result += countAllTrailsToTop(data, 0, i, j);
                }
            }
        }
        return result;
    }

    private void countUniqueTrailsToTop(int[][] data, int currentHeight, int i, int j, long startPosition, Map<Long, Set<Long>> trailHeads) {
        if (currentHeight == 9) {
            trailHeads.computeIfAbsent(startPosition, _ -> new HashSet<>()).add(hashPosition(i, j));
            return;
        }

        for (int dir = 0; dir < 4; dir++) {
            int newRow = i + dRow[dir];
            int newCol = j + dCol[dir];
            if (isWithinBounds(newRow, newCol, data) && data[newRow][newCol] == currentHeight + 1) {
                countUniqueTrailsToTop(data, currentHeight + 1, newRow, newCol, startPosition, trailHeads);
            }
        }
    }

    private long hashPosition(int i, int j) {
        return i * 1000L + j;
    }

    private boolean isWithinBounds(int newRow, int newCol, int[][] data) {
        return newRow >= 0 && newRow < data.length && newCol >= 0 && newCol < data[newRow].length;
    }

    private long countAllTrailsToTop(int[][] data, int currentHeight, int i, int j) {
        var result = 0L;
        if (currentHeight == 9) {
            return 1;
        }

        for (int dir = 0; dir < 4; dir++) {
            int newRow = i + dRow[dir];
            int newCol = j + dCol[dir];
            if (isWithinBounds(newRow, newCol, data) && data[newRow][newCol] == currentHeight + 1) {
                result += countAllTrailsToTop(data, currentHeight + 1, newRow, newCol);
            }
        }
        return result;
    }
}

//TODO add Tests

//Should return 5
//0190999
//9991998
//9992997
//6543456
//7659987
//8769999
//9879999

//Should return 2
//1011911
//2111811
//3111711
//4567654
//1118113
//1119112
//1111101

//Should return 9
//89010123
//78121874
//87430965
//96549874
//45678903
//32019012
//01329801
//10456732

//TASK SHOULD RETURN 794

//TASK 2 SHOULD RETURN 1706