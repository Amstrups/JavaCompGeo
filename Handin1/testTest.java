import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class testTest {

    public static void main(String[] args) {
        int iterations = 10;
        tester(1, 4); // Throw away stats - Cold start
        // tester(1, 1); // Throw away stats - Cold start
        // tester(1,iterations);
        // tester(2,iterations);
        // tester(3,iterations);
        tester(4,iterations);
        // tester(5,iterations);
        tester(8,iterations);
        tester(16,iterations);
        // tester(32,iterations);
        // tester(64,iterations);
    }

    private static void tester(int threads, int iterations) {
        long total = 0;
        for (int i = 0; i < iterations; i++) {
            total += testParallelMergeSortWithDifferentThreadCounts(threads);  
        }
        total = total / iterations; 
        System.out.println("Average of " + iterations + " iterations with " + threads + " threads, is " + total + " micro seconds");
    }

    static int[] intArray;

    public static long testParallelMergeSortWithDifferentThreadCounts(int threadCount) {
        // Read the array from a file
        // try {
        //     BufferedReader reader = new BufferedReader(new FileReader("array.txt"));
        //     String line = reader.readLine();
        //     String[] stringArray = line.split(",");
        //     intArray = new int[stringArray.length];
        //     for (int i = 0; i < stringArray.length; i++) {
        //         intArray[i] = Integer.parseInt(stringArray[i]);
        //     }
        //     reader.close();
        // } catch (IOException e) {
        //     System.out.println("Error reading the file.");
        //     e.printStackTrace();
        // }

        // Create a comparator array
        // int[] comparator = intArray.clone();
        // Arrays.sort(comparator);

        ArrayGenerator a = new ArrayGenerator(1000000);
        a.SortedArray();

        // Perform the parallel merge sort
        long startTime = System.nanoTime();
        int[] result = BasicParallelMergeSort.parallelMergeSort(a.array, threadCount);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000; // Duration in microseconds

        long startTimeNew = System.nanoTime();
        int[] resultNew = test.parallelMergeSort(a.array, threadCount);
        long endTimeNew = System.nanoTime();
        long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

        if (Arrays.equals(a.sorted, result)) {
            System.out.println("old time: " + duration + " microseconds");
        }

        if (Arrays.equals(a.sorted, resultNew)) {
            System.out.println("new time: " + durationNew + " microseconds");
        } else {
            System.out.println("fuck");
        }
        // System.out.println("Threads: " + threadCount + ", Duration: " + duration + " microseconds");
        return durationNew;
    }
}
