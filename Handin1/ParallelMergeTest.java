import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ParallelMergeTest {

    public static void main(String[] args) {
        int iterations = 25;
        tester(1, 10); // Throw away stats - Cold start
        tester(1,iterations);
        tester(2,iterations);
        // tester(3,iterations);
        tester(4,iterations);
        // tester(5,iterations);
        tester(8,iterations);
        tester(16,iterations);
        tester(32,iterations);
        tester(64,iterations);
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
        int[] first = Arrays.copyOfRange(comparator, 0, comparator.length / 2);
        int[] second = Arrays.copyOfRange(comparator, comparator.length / 2, comparator.length);
        Arrays.sort(first);
        Arrays.sort(second);
        Arrays.sort(comparator);
        int[] both = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, both, first.length, second.length);
    

        // Perform the parallel merge sort
        long startTime = System.nanoTime();
        int[] result = ParallelMerge.parallelMerge(first, second, new int[first.length + second.length], 8);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000; // Duration in microseconds

        assertArrayEquals(comparator, result);
        // System.out.println("Threads: " + threadCount + ", Duration: " + duration + " microseconds");
        return duration;
    }
}
