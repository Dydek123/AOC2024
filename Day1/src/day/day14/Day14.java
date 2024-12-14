package day.day14;

import day.Day;
import day.DayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Day14 extends Day {

    private static final Pattern GET_VALUES_PATTERN = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");

    public Day14() {
        setDayName("14");
    }

    @Override
    public Long taskOne(String filePath) {
        var iterations = 100;
        var boardRecord = determineBoardSize(filePath);
        var guards = parseGuards(filePath);
        for (int i = 0; i < iterations; i++) {
            moveGuards(guards, boardRecord.sizeX(), boardRecord.sizeY());
        }
        return calculateResult(guards, boardRecord.sizeX(), boardRecord.sizeY());
    }

    @Override
    public Long taskTwo(String filePath) {
        var boardRecord = determineBoardSize(filePath);
        var guards = parseGuards(filePath);
        char[][] board = new char[boardRecord.sizeY()][boardRecord.sizeX()];
        setDefaultBoardValues(board);

        var seconds = 0L;
        var maxSeconds = 10000L;
        while (seconds++ < maxSeconds) {
            moveGuards(guards, boardRecord.sizeX(), boardRecord.sizeY());
            setGuardPositionOnBoard(guards, board);
            for (int j = 0; j < boardRecord.sizeY(); j++) {
                if (containsSequence(board[j], '#', 10)) {
                    DayUtils.printGrid(board);
                    return seconds;
                }
            }
        }
        System.out.println("NO SOLUTION FOUND FOR LIMIT SECONDS: " + maxSeconds);
        return 0L;
    }

    private Board determineBoardSize(String filePath) {
        if (filePath.contains("input-test.txt")) {
            return new Board(11, 7);
        }
        return new Board(101, 103);
    }

    private Set<Guard> parseGuards(String filePath) {
        try {
            var guards = new HashSet<Guard>();
            Files.lines(Path.of(filePath)).forEach(line -> {
                var matcher = GET_VALUES_PATTERN.matcher(line);
                if (matcher.find()) {
                    guards.add(new Guard(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4))
                    ));
                }
            });
            return guards;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean containsSequence(char[] row, char target, int length) {
        int count = 0;
        for (char c : row) {
            count = (c == target) ? count + 1 : 0;
            if (count >= length) {
                return true;
            }
        }
        return false;
    }

    private void setDefaultBoardValues(char[][] board) {
        for (char[] chars : board) {
            Arrays.fill(chars, '.');
        }
    }

    private void moveGuards(Set<Guard> guards, int boardSizeX, int boardSizeY) {
        for (var guard : guards) {
            var newXPosition = guard.getPosX() + guard.getVelocityX();
            var newYPosition = guard.getPosY() + guard.getVelocityY();
            guard.setPosX(newXPosition >= 0 ? newXPosition % boardSizeX : boardSizeX + newXPosition);
            guard.setPosY(newYPosition >= 0 ? newYPosition % boardSizeY : boardSizeY + newYPosition);
        }
    }

    private void setGuardPositionOnBoard(Set<Guard> guards, char[][] board) {
        setDefaultBoardValues(board);
        for (var guard : guards) {
            board[guard.getPosY()][guard.getPosX()] = '#';
        }
    }

    private long calculateResult(Set<Guard> guards, int boardSizeX, int boardSizeY) {
        var halfX = boardSizeX / 2;
        var halfY = boardSizeY / 2;

        long topLeft = 0, topRight = 0, bottomLeft = 0, bottomRight = 0;

        for (Guard guard : guards) {
            int x = guard.getPosX();
            int y = guard.getPosY();

            if (x < halfX && y < halfY) {
                topLeft++;
            } else if (x > halfX && y < halfY) {
                topRight++;
            } else if (x < halfX && y > halfY) {
                bottomLeft++;
            } else if (x > halfX && y > halfY) {
                bottomRight++;
            }
        }

        return topLeft * topRight * bottomLeft * bottomRight;
    }
}
//TODO ADD TESTS