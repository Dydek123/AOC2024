package day6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    void testTask1() {
        var resultTest = Main.task1("/home/vboxuser/Desktop/AOC2024/Day1/src/day6/input-test.txt");
        var resultTask = Main.task1("/home/vboxuser/Desktop/AOC2024/Day1/src/day6/input-task.txt");
        assertEquals(41, resultTest);
        assertEquals(4454, resultTask);
    }

    @Test
    void testTask2() {
        var resultTest = Main.task2("/home/vboxuser/Desktop/AOC2024/Day1/src/day6/input-test.txt");
//        var resultTask = Main.task2("/home/vboxuser/Desktop/AOC2024/Day1/src/day6/input-task.txt");
//        assertEquals(6, resultTest);
//        assertEquals(4454, resultTask);
    }
}