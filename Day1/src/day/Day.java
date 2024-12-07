package day;

import java.io.IOException;

public abstract class Day {

    @FunctionalInterface
    public interface Operation {
        Long process();
    }

    public void runAllTest() {
        var filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day/day7/input-test.txt";
        processTask(() -> taskOne(filePath), 1);
        processTask(() -> taskTwo(filePath), 2);
    }

    public void runAllTask() {
        var filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day/day7/input-task.txt";
        processTask(() -> taskOne(filePath), 1);
        processTask(() -> taskTwo(filePath), 2);
    }

    public void runFirstTask() {
        var filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day/day7/input-task.txt";
        processTask(() -> taskOne(filePath), 1);
    }

    public void runSecondTask() {
        var filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day/day7/input-task.txt";
        processTask(() -> taskTwo(filePath), 2);
    }

    private void processTask(Operation operation, int taskNumber) {
        long startTime = System.currentTimeMillis();
        var task1Result = operation.process();
        long endTime = System.currentTimeMillis();

        System.out.println("Task " + taskNumber + " result: " + task1Result + " elapsed time: " + (endTime - startTime) + "ms");
    }

    public abstract Long taskOne(String filePath);

    public abstract Long taskTwo(String filePath);
}