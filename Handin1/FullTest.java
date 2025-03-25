import java.lang.Math;
import java.util.Arrays;
import java.io.File; 
import java.io.*; 

public class FullTest {
    public static void main(String[] args) {
        int[] threadCounts = {1,1,2,4,8,12}; // throw away first value 
        // int[] threadCounts = {1}; // throw away first value 

        // Runs each mergesort this amount of time on same input and takes average of time
        int iterationsToAverageOver = 10; 

        int arraySizePower10UpperBound = 7; 
        // For ^this = 7, inputs are siez 10^1, 10^2, 10^3,...,10^7
        // Read lines 63-65

        timeReverseSorted(threadCounts, iterationsToAverageOver, arraySizePower10UpperBound);
        timeRandomArray(threadCounts, iterationsToAverageOver, arraySizePower10UpperBound);
        timeNonZeroValues(threadCounts, iterationsToAverageOver, arraySizePower10UpperBound);
        timePartitionedSorted(threadCounts, iterationsToAverageOver, arraySizePower10UpperBound);
        // testParallelMerge(threadCounts, iterationsToAverageOver, arraySizePower10UpperBound);
        // testParallelMerge2(threadCounts, iterationsToAverageOver, arraySizePower10UpperBound);
        // testParallelMerge3(threadCounts, iterationsToAverageOver, arraySizePower10UpperBound);
    }   

    public static void writeArrayToFile(String filename, long[][] array, int[] threadCounts) {
        String fullFilename = "./Rapports/" + filename + ".csv";
        try {
            File rapportsDirectory = new File("./Rapports");
            rapportsDirectory.mkdir();
            File myObj = new File(fullFilename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(fullFilename));
        String csvHeader = "threads";
        for (int i = 1; i <= array[0].length; i++) {
            csvHeader += (", 10^"+i);
        }
        csvHeader += "\n";
        outputWriter.write(csvHeader);
        for (int i = 0; i < array.length; i++) {
          // Maybe:
          String rowString = Arrays.toString(array[i]);

          outputWriter.write(threadCounts[i] + ", " + rowString.substring(1, rowString.length()-2) + "\n");
        }
        outputWriter.flush();  
        outputWriter.close(); 
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void timeReverseSorted(int[] threadCounts, int iterations, int powerLimit) {
        long[][] basicResults = new long[threadCounts.length][powerLimit];
        long[][] parallelResults = new long[threadCounts.length][powerLimit]; 

        int basicErrors = 0;
        int parallelErrors = 0;

        for (int i = 0; i < powerLimit; i++) {
            int n = (int) Math.pow(10, i+1);
            ArrayGenerator a = new ArrayGenerator(n);
            a.ReverseSortedArray();
            System.out.println("Run " + (i+1) + "/" + powerLimit + ", N = " + n);
            for (int j = 0; j < threadCounts.length; j++) {
                long basicTotalDuration = 0;
                long parallelTotalDuration = 0;
                for (int k = 0; k < iterations; k++) {
                    long startTime = System.nanoTime();
                    int[] result = BasicParallelMergeSort.parallelMergeSort(a.array, threadCounts[j]);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, result)) {
                        basicErrors++;
                    }
                    basicTotalDuration += (endTime-startTime);

                    long startTimeNew = System.nanoTime();
                    int[] resultNew = test.parallelMergeSort(a.array, threadCounts[j]);
                    long endTimeNew = System.nanoTime();
                    long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, resultNew)) {
                        basicErrors++;
                    }
                    parallelTotalDuration += (endTimeNew-startTimeNew);

                }
                basicTotalDuration = basicTotalDuration / iterations;
                parallelTotalDuration = parallelTotalDuration / iterations;
                basicResults[j][i] = basicTotalDuration;
                parallelResults[j][i] = parallelTotalDuration;
            }
        }
        // Paste here for verbose summary
        writeArrayToFile("reverse_basic", basicResults, threadCounts);
        writeArrayToFile("reverse_parallel", parallelResults,threadCounts);
    }


    public static void timeRandomArray(int[] threadCounts, int iterations, int powerLimit) {
        long[][] basicResults = new long[threadCounts.length][powerLimit];
        long[][] parallelResults = new long[threadCounts.length][powerLimit]; 

        int basicErrors = 0;
        int parallelErrors = 0;

        for (int i = 0; i < powerLimit; i++) {
            int n = (int) Math.pow(10, i+1);
            ArrayGenerator a = new ArrayGenerator(n);
            a.RandomArray();
            System.out.println("Run " + (i+1) + "/" + powerLimit + ", N = " + n);
            for (int j = 0; j < threadCounts.length; j++) {
                long basicTotalDuration = 0;
                long parallelTotalDuration = 0;
                for (int k = 0; k < iterations; k++) {
                    long startTime = System.nanoTime();
                    int[] result = BasicParallelMergeSort.parallelMergeSort(a.array, threadCounts[j]);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, result)) {
                        basicErrors++;
                    }
                    basicTotalDuration += (endTime-startTime);

                    long startTimeNew = System.nanoTime();
                    int[] resultNew = test.parallelMergeSort(a.array, threadCounts[j]);
                    long endTimeNew = System.nanoTime();
                    long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, resultNew)) {
                        basicErrors++;
                    }
                    parallelTotalDuration += (endTimeNew-startTimeNew);

                }
                basicTotalDuration = basicTotalDuration / iterations;
                parallelTotalDuration = parallelTotalDuration / iterations;
                basicResults[j][i] = basicTotalDuration;
                parallelResults[j][i] = parallelTotalDuration;
            }
        }
        // Paste here for verbose
        writeArrayToFile("partition_basic", basicResults, threadCounts);
        writeArrayToFile("random_basic", basicResults, threadCounts);
        writeArrayToFile("random_parallel", parallelResults,threadCounts);
    }


    public static void timePartitionedSorted(int[] threadCounts, int iterations, int powerLimit) {
        long[][] basicResults = new long[threadCounts.length][powerLimit];
        long[][] parallelResults = new long[threadCounts.length][powerLimit]; 

        int basicErrors = 0;
        int parallelErrors = 0;

        for (int i = 0; i < powerLimit; i++) {
            int n = (int) Math.pow(10, i+1);
            ArrayGenerator a = new ArrayGenerator(n);
            a.PartitionedSortedArray();
            System.out.println("Run " + (i+1) + "/" + powerLimit + ", N = " + n);
            for (int j = 0; j < threadCounts.length; j++) {
                long basicTotalDuration = 0;
                long parallelTotalDuration = 0;
                for (int k = 0; k < iterations; k++) {
                    long startTime = System.nanoTime();
                    int[] result = BasicParallelMergeSort.parallelMergeSort(a.array, threadCounts[j]);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, result)) {
                        basicErrors++;
                    }
                    basicTotalDuration += (endTime-startTime);

                    long startTimeNew = System.nanoTime();
                    int[] resultNew = test.parallelMergeSort(a.array, threadCounts[j]);
                    long endTimeNew = System.nanoTime();
                    long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, resultNew)) {
                        basicErrors++;
                    }
                    parallelTotalDuration += (endTimeNew-startTimeNew);

                }
                basicTotalDuration = basicTotalDuration / iterations;
                parallelTotalDuration = parallelTotalDuration / iterations;
                basicResults[j][i] = basicTotalDuration;
                parallelResults[j][i] = parallelTotalDuration;
            }
        }
        // Paste here for verbose
        writeArrayToFile("partition_basic", basicResults, threadCounts);
        writeArrayToFile("partition_parallel", parallelResults,threadCounts);
    }

    public static void timeNonZeroValues(int[] threadCounts, int iterations, int powerLimit) {
        long[][] basicResults = new long[threadCounts.length][powerLimit];
        long[][] parallelResults = new long[threadCounts.length][powerLimit]; 

        int basicErrors = 0;
        int parallelErrors = 0;

        for (int i = 0; i < powerLimit; i++) {
            int n = (int) Math.pow(10, i+1);
            ArrayGenerator a = new ArrayGenerator(n);
            a.NonZeroValues((int) Math.floor(n*0.1));
            System.out.println("Run " + (i+1) + "/" + powerLimit + ", N = " + n);
            for (int j = 0; j < threadCounts.length; j++) {
                long basicTotalDuration = 0;
                long parallelTotalDuration = 0;
                for (int k = 0; k < iterations; k++) {
                    long startTime = System.nanoTime();
                    int[] result = BasicParallelMergeSort.parallelMergeSort(a.array, threadCounts[j]);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, result)) {
                        basicErrors++;
                    }
                    basicTotalDuration += (endTime-startTime);

                    long startTimeNew = System.nanoTime();
                    int[] resultNew = test.parallelMergeSort(a.array, threadCounts[j]);
                    long endTimeNew = System.nanoTime();
                    long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, resultNew)) {
                        basicErrors++;
                    }
                    parallelTotalDuration += (endTimeNew-startTimeNew);

                }
                basicTotalDuration = basicTotalDuration / iterations;
                parallelTotalDuration = parallelTotalDuration / iterations;
                basicResults[j][i] = basicTotalDuration;
                parallelResults[j][i] = parallelTotalDuration;
            }
        }
        // Paste here for verbose
        writeArrayToFile("nonzero_basic", basicResults, threadCounts);
        writeArrayToFile("nonzero_parallel", parallelResults,threadCounts);

    }
         
    public static void testParallelMerge(int[] threadCounts, int iterations, int powerLimit) {
        long[][] basicResults = new long[threadCounts.length][powerLimit];
        long[][] parallelResults = new long[threadCounts.length][powerLimit]; 

        int basicErrors = 0;
        int parallelErrors = 0;

        for (int i = 0; i < powerLimit; i++) {
            int n = (int) Math.pow(10, i+1);
            ArrayGenerator a = new ArrayGenerator(n);
            a.PartitionedSortedArray();
            System.out.println("Run " + (i+1) + "/" + powerLimit + ", N = " + n);
            for (int j = 0; j < threadCounts.length; j++) {
                long basicTotalDuration = 0;
                long parallelTotalDuration = 0;
                for (int k = 0; k < iterations; k++) {

                    long startTime = System.nanoTime();
                    int[] scratch = new int[a.sorted.length];
                    BasicParallelMergeSort.merge2(a.array, scratch,0,a.partitionIndex, n);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, scratch)) {
                        basicErrors++;
                    }
                    basicTotalDuration += (endTime-startTime);

                    long startTimeNew = System.nanoTime();
                    int[] resultNew = ParallelMerge2.parallelMerge(
                        a.array, 
                        0, 
                        a.partitionIndex-1, 
                        a.partitionIndex,
                        a.array.length-1,
                        0,
                        threadCounts[j]
                    );
                    long endTimeNew = System.nanoTime();
                    long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, resultNew)) {
                        basicErrors++;
                    }
                    parallelTotalDuration += (endTimeNew-startTimeNew);

                }
                basicTotalDuration = basicTotalDuration / iterations;
                parallelTotalDuration = parallelTotalDuration / iterations;
                basicResults[j][i] = basicTotalDuration;
                parallelResults[j][i] = parallelTotalDuration;
            }
        }
        // Paste here for verbose
        System.out.println("Basic errors: " + basicErrors);
        System.out.println("Parallel errors: " + parallelErrors);
        writeArrayToFile("merge1_basic", basicResults, threadCounts);
        writeArrayToFile("merge1_parallel", parallelResults,threadCounts);
    }
    public static void testParallelMerge2(int[] threadCounts, int iterations, int powerLimit) {
        long[][] basicResults = new long[threadCounts.length][powerLimit];
        long[][] parallelResults = new long[threadCounts.length][powerLimit]; 

        int basicErrors = 0;
        int parallelErrors = 0;

        for (int i = 0; i < powerLimit; i++) {
            int n = (int) Math.pow(10, i+1);
            ArrayGenerator a = new ArrayGenerator(n);
            a.PartitionedSortedArray2();
            System.out.println("Run " + (i+1) + "/" + powerLimit + ", N = " + n);
            for (int j = 0; j < threadCounts.length; j++) {
                long basicTotalDuration = 0;
                long parallelTotalDuration = 0;
                for (int k = 0; k < iterations; k++) {

                    long startTime = System.nanoTime();
                    int[] scratch = new int[a.sorted.length];
                    BasicParallelMergeSort.merge2(a.array, scratch,0,a.partitionIndex, n);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, scratch)) {
                        basicErrors++;
                    }
                    basicTotalDuration += (endTime-startTime);

                    long startTimeNew = System.nanoTime();
                    int[] resultNew = ParallelMerge2.parallelMerge(
                        a.array, 
                        0, 
                        a.partitionIndex-1, 
                        a.partitionIndex,
                        a.sorted.length-1,
                        0,
                        threadCounts[j]
                    );
                    long endTimeNew = System.nanoTime();
                    long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, resultNew)) {
                        basicErrors++;
                    }
                    parallelTotalDuration += (endTimeNew-startTimeNew);

                }
                basicTotalDuration = basicTotalDuration / iterations;
                parallelTotalDuration = parallelTotalDuration / iterations;
                basicResults[j][i] = basicTotalDuration;
                parallelResults[j][i] = parallelTotalDuration;
            }
        }
        // Paste here for verbose
        System.out.println("Basic errors: " + basicErrors);
        System.out.println("Parallel errors: " + parallelErrors);
        writeArrayToFile("merge2_basic", basicResults, threadCounts);
        writeArrayToFile("merge2_parallel", parallelResults,threadCounts);
    }

    public static void testParallelMerge3(int[] threadCounts, int iterations, int powerLimit) {
        long[][] basicResults = new long[threadCounts.length][powerLimit];
        long[][] parallelResults = new long[threadCounts.length][powerLimit]; 

        int basicErrors = 0;
        int parallelErrors = 0;

        for (int i = 0; i < powerLimit; i++) {
            int n = (int) Math.pow(10, i+1);
            ArrayGenerator a = new ArrayGenerator(n);
            a.PartitionedSortedArray2();
            System.out.println("Run " + (i+1) + "/" + powerLimit + ", N = " + n);
            for (int j = 0; j < threadCounts.length; j++) {
                long basicTotalDuration = 0;
                long parallelTotalDuration = 0;
                for (int k = 0; k < iterations; k++) {

                    long startTime = System.nanoTime();
                    int[] scratch = new int[a.sorted.length];
                    BasicParallelMergeSort.merge2(a.sorted, scratch,0,a.partitionIndex, n);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, scratch)) {
                        basicErrors++;
                    }
                    basicTotalDuration += (endTime-startTime);

                    long startTimeNew = System.nanoTime();
                    int[] resultNew = ParallelMerge2.parallelMerge(
                        a.sorted, 
                        0, 
                        a.partitionIndex-1, 
                        a.partitionIndex,
                        a.sorted.length-1,
                        0,
                        threadCounts[j]
                    );
                    long endTimeNew = System.nanoTime();
                    long durationNew = (endTimeNew - startTimeNew) / 1000; // Duration in microseconds

                    if (!Arrays.equals(a.sorted, resultNew)) {
                        parallelErrors++;
                    }
                    parallelTotalDuration += (endTimeNew-startTimeNew);

                }
                basicTotalDuration = basicTotalDuration / iterations;
                parallelTotalDuration = parallelTotalDuration / iterations;
                basicResults[j][i] = basicTotalDuration;
                parallelResults[j][i] = parallelTotalDuration;
            }
        }
        // Paste here for verbose
        System.out.println("Basic errors: " + basicErrors);
        System.out.println("Parallel errors: " + parallelErrors);
        writeArrayToFile("merge3_basic", basicResults, threadCounts);
        writeArrayToFile("merge3_parallel", parallelResults,threadCounts);
    }
    
}
// Verbose summary copy pasta
/*
        System.out.println("Basic merge times:");
        for (int i = 0; i < threadCounts.length; i++) {
            System.out.println("Threads: "+ threadCounts[i] + ", durations: " + Arrays.toString(basicResults[i]));   
        }
        System.out.println("Parallel merge times:");
        for (int i = 0; i < threadCounts.length; i++) {
            System.out.println("Threads: "+ threadCounts[i] + ", durations: " + Arrays.toString(parallelResults[i]));   
        }
        System.out.println("Basic errors: " + basicErrors);
        System.out.println("Parallel errors: " + parallelErrors);
 */