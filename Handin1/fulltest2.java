import java.lang.Math;
import java.util.Arrays;

public class fulltest2 {
    public static void main(String[] args) {
        int[] threadCounts = {1,1,2,4,8,12};

        int iterations = 10;

        int arraySizePower10UpperBound = 7;

        // timeReverseSorted(threadCounts, iterations, arraySizePower10UpperBound);
        // timeRandomArray(threadCounts, iterations, arraySizePower10UpperBound);
        // timeNonZeroValues(threadCounts, iterations, arraySizePower10UpperBound);
        timePartitionedSorted(threadCounts, iterations, arraySizePower10UpperBound);

    }   

    public void writeToFile(String path) {
        
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
    }
}