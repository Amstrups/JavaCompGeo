import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FinalParallelMergeTest {

    public static void main(String[] args) {
        tester(1,25);
        tester(2,25);
        tester(4,25);
        tester(8,25);
        tester(16,25);
        tester(32,25);
        tester(64,25);
    }

    private static void tester(int threads, int iterations) {
        long total = 0;
        for (int i = 0; i < iterations; i++) {
            total += testParallelMergeSortWithDifferentThreadCounts(threads);  
        }
        total = total / iterations; 
        System.out.println("Average of " + threads + " threads, is " + total + " ms ");
    }

    static int[] intArray;

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 4, 8 })
    public static long testParallelMergeSortWithDifferentThreadCounts(int threadCount) {
        // Read the array from a file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("array.txt"));
            String line = reader.readLine();
            String[] stringArray = line.split(",");
            intArray = new int[stringArray.length];
            for (int i = 0; i < stringArray.length; i++) {
                intArray[i] = Integer.parseInt(stringArray[i]);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }

        // Create a comparator array
        int[] comparator = intArray.clone();
        Arrays.sort(comparator);

        // Perform the parallel merge sort
        long startTime = System.nanoTime();
        System.out.println(intArray[0]);
        System.out.println(intArray[1]);
        System.out.println(intArray[2]);
        System.out.println(intArray[3]);
        System.out.println(intArray[4]);
        System.out.println(intArray[5]);
        System.out.println(intArray[6]);
        int[] result = test.parallelMergeSort(intArray, threadCount);
        System.out.println(result[0]);
        System.out.println(result[1]);
        System.out.println(result[2]);
        System.out.println(result[3]);
        System.out.println(result[4]);
        System.out.println(result[5]);
        System.out.println(result[6]);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000; // Duration in milliseconds

        assertArrayEquals(comparator, result);
        // System.out.println("Threads: " + threadCount + ", Duration: " + duration + " ms");
        return duration;
    }
}
