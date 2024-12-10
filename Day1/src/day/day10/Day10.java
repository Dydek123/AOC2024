package day.day10;

import day.Day;
import day.DayUtils;

import java.util.*;

public class Day10 extends Day {

    long result1 = 0;
    Map<Long, Set<Long>> trailHeads = new HashMap<>();

    public Day10() {
        setDayName("10");
    }

    @Override
    public Long taskOne(String filePath) {
        trailHeads.clear();
        var data = DayUtils.readGridFromFileAsNumbers(filePath);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    findTrail(data, 0, i, j, i * 1000L + j, new HashSet<>());
                }
            }
        }
        return trailHeads.values().stream().mapToLong(Set::size).sum();
    }

    private void findTrail(int[][] data, int currentHeight, int i, int j, long startPosition, Set<Long> visited) {
        long position = i * 1000L + j;

        if (visited.contains(position)) {
            return;
        }
        visited.add(position);

        if (currentHeight == 9) {
            trailHeads.computeIfAbsent(startPosition, k -> new HashSet<>()).add(position);
            return;
        }

        //UP
        if (i - 1 >= 0 && data[i - 1][j] == currentHeight + 1) {
            findTrail(data, currentHeight + 1, i - 1, j, startPosition, visited);
        }

        //RIGHT
        if (j + 1 < data[i].length && data[i][j + 1] == currentHeight + 1) {
            findTrail(data, currentHeight + 1, i, j + 1, startPosition, visited);
        }

        //DOWN
        if (i + 1 < data.length && data[i + 1][j] == currentHeight + 1) {
            findTrail(data, currentHeight + 1, i + 1, j, startPosition, visited);
        }

        //LEFT
        if (j - 1 >= 0 && data[i][j - 1] == currentHeight + 1) {
            findTrail(data, currentHeight + 1, i, j - 1, startPosition, visited);
        }

        return;
    }

    private void findTrail2(int[][] data, int currentHeight, int i, int j) {
        if (currentHeight == 9) {
            result1++;
            return;
        }
        //UP
        if (i - 1 >= 0 && data[i - 1][j] == currentHeight + 1) {
            findTrail2(data, currentHeight + 1, i - 1, j);
        }

        //RIGHT
        if (j + 1 < data.length && data[i][j + 1] == currentHeight + 1) {
            findTrail2(data, currentHeight + 1, i, j + 1);
        }

        //DOWN
        if (i + 1 < data.length && data[i + 1][j] == currentHeight + 1) {
            findTrail2(data, currentHeight + 1, i + 1, j);
        }

        //LEFT
        if (j - 1 >= 0 && data[i][j - 1] == currentHeight + 1) {
            findTrail2(data, currentHeight + 1, i, j - 1);
        }

        return;
    }

    @Override
    public Long taskTwo(String filePath) {
        trailHeads.clear();
        result1 = 0;
        var data = DayUtils.readGridFromFileAsNumbers(filePath);
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (data[i][j] == 0) {
                    findTrail2(data, 0, i, j);
                }
            }
        }
        return result1;
    }
}


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