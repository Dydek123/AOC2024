package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        var list1 = new LinkedList<Integer>();
        var list2 = new LinkedList<Integer>();

        list1.add(3);
        list1.add(4);
        list1.add(2);
        list1.add(1);
        list1.add(3);
        list1.add(3);
        //3 2 0 4 5 1


        list2.add(4);
        list2.add(3);
        list2.add(5);
        list2.add(3);
        list2.add(9);
        list2.add(3);
        //1 3 5 0 2 4

        //2 1 5 4 3 3 = 18

        var sorted1 = list1.stream().sorted().collect(Collectors.toList());
        var sorted2 = list2.stream().sorted().collect(Collectors.toList());

        var result = 0;
        for (int i = 0; i < sorted1.size(); i++) {
            result += Math.abs(sorted2.get(i) - sorted1.get(i));
        }
//        System.out.println(result);
        System.out.println(getDataFromFile());
        System.out.println(getDataFromFileAdvanced());
    }

    private static List<Integer> getDataFromFile() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        // Specify the file path
        String filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/input.txt";  // Replace with the actual path to your file

        // Read data from file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line by space to get the two numbers
                String[] parts = line.split("\\s+");

                // Convert the string values to integers and add to the respective lists
                list1.add(Integer.parseInt(parts[0]));
                list2.add(Integer.parseInt(parts[1]));
            }

            var sorted1 = list1.stream().sorted().collect(Collectors.toList());
            var sorted2 = list2.stream().sorted().collect(Collectors.toList());

            var result = 0;
            for (int i = 0; i < sorted1.size(); i++) {
                result += Math.abs(sorted2.get(i) - sorted1.get(i));
            }
            System.out.println(result);

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        return list1;
    }

    private static List<Integer> getDataFromFileAdvanced() {
        System.out.println("ADVANCED");
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        // Specify the file path
        String filePath = "/home/vboxuser/Desktop/AOC2024/Day1/src/input.txt";  // Replace with the actual path to your file

        // Read data from file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line by space to get the two numbers
                String[] parts = line.split("\\s+");

                // Convert the string values to integers and add to the respective lists
                list1.add(Integer.parseInt(parts[0]));
                list2.add(Integer.parseInt(parts[1]));
            }

            // Print the lists (optional, for verification)
            System.out.println("List 1: " + list1);
            System.out.println("List 2: " + list2);

            var result = 0;
            for (int i = 0; i < list1.size(); i++) {
                var leftElement = list1.get(i);
                var iteration = (int) (leftElement * list2.stream().filter(el -> el.equals(leftElement)).count());
                result += iteration;
                System.out.println("Left element: " + leftElement + " occurencies in right: " + iteration);
            }
            System.out.println(result);

        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        return list1;
    }
}