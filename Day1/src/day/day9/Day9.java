package day.day9;

import day.Day;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 extends Day {

    public Day9() {
        setDayName("9");
    }

    @Override
    public Long taskOne(String filePath) {
        try {
            int[] data = Arrays.stream(Files.readString(Paths.get(filePath)).split(""))
                    .flatMapToInt(num -> IntStream.of(Integer.parseInt(num)))
                    .toArray();
            var counter = 0;
            List<Integer> finalData = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i]; j++) {
                    finalData.add(i % 2 == 0 ? counter : -1);
                }
                if (i % 2 == 0) {
                    counter++;
                }
            }

            for (int i = finalData.size() - 1; i >= 0; i--) {
                if (finalData.get(i) == -1) {
                    finalData.remove(i);
                } else {
                    var emptyBlockIndex = finalData.indexOf(-1);
                    if (emptyBlockIndex != -1) {
                        finalData.set(emptyBlockIndex, finalData.get(i));
                        finalData.remove(i);
                    }
                }
            }

            return IntStream.range(0, finalData.size())
                    .mapToLong(i -> finalData.get(i) * (long) i)
                    .sum();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long taskTwo(String filePath) {
        try {
            //Prepare data
            int[] data = Arrays.stream(Files.readString(Paths.get(filePath)).split(""))
                    .flatMapToInt(num -> IntStream.of(Integer.parseInt(num)))
                    .toArray();
            var counter = 0;
            var occurencesMap = new TreeMap<Integer, Integer>();
            List<Integer> finalData = new ArrayList<>();
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i]; j++) {
                    finalData.add(i % 2 == 0 ? counter : -1);
                }
                if (i % 2 == 0) {
                    occurencesMap.put(counter, data[i]);
                    counter++;
                }
            }

            //Create map with empty blocks (key is index where empty block starts, value is amount of consecutive empty blocks
            var emptyBlockMap = new TreeMap<Integer, Integer>();
            var startIndex = 0;
            for (int i = 1; i < finalData.size(); i++) {
                if (finalData.get(i) == -1 && finalData.get(i - 1) != -1) {
                    startIndex = i;
                    emptyBlockMap.put(startIndex, 1);
                } else if (finalData.get(i) == -1 && finalData.get(i - 1) == -1) {
                    emptyBlockMap.put(startIndex, emptyBlockMap.get(startIndex) + 1);
                }
            }
            //Move items
            var processedIds = new HashSet<Integer>();
            for (int i = finalData.size() - 1; i > 0; i--) {
                var value = finalData.get(i);
                if (value != -1 && !processedIds.contains(value)) {
                    var howMany = occurencesMap.get(value);
                    var whereToInsert = emptyBlockMap.entrySet().stream().filter(el -> el.getValue() >= howMany).findFirst();
                    if (whereToInsert.isPresent() && whereToInsert.get().getKey() < i) {
                        var present = whereToInsert.get();
                        for (int j = 0; j < howMany; j++) {
                            finalData.set(present.getKey() + j, value);
                            finalData.set(i - j, -1);
                            emptyBlockMap.put(present.getKey(), emptyBlockMap.get(present.getKey()) - 1);
                        }
                        if (present.getValue() > 0) {
                            emptyBlockMap.put(present.getKey() + howMany, present.getValue());
                        }
                        emptyBlockMap.remove(present.getKey());
                    }
                    processedIds.add(value);
                }
            }

            return IntStream.range(0, finalData.size())
                    .mapToLong(i -> finalData.get(i) != -1 ? finalData.get(i) * (long) i : 0)
                    .sum();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Input-test.txt should return 2858
        // 12345 should return 132
        //Input-task.txt should return 6307653242596
    }
}
