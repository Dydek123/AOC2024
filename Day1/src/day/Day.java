package day;

public abstract class Day {

    @FunctionalInterface
    public interface Operation {
        int process();
    }

    public void runAllTest() {
        var filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day6/input-test.txt";
        processTask(() -> taskOne(filePath));
        processTask(() -> taskTwo(filePath));
    }

    public void runAllTask() {
        var filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/day.day6/input-task.txt";
        processTask(() -> taskOne(filePath));

        processTask(() -> taskTwo(filePath));
    }

    private void processTask(Operation operation) {
        long startTime = System.currentTimeMillis();
        var task1Result = operation.process();
        long endTime = System.currentTimeMillis();

        System.out.println("Result: " + task1Result + " elapsed time: " + (endTime - startTime) + "ms");
    }

    public abstract int taskOne(String filePath);

    public abstract int taskTwo(String filePath);
}