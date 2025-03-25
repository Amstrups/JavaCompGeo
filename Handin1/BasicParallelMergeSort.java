import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class BasicParallelMergeSort {

    public static void main(String[] args) {
        int[] arr = { 5, 1, 8, 4, 2, 7, 6, 3 };
        int[] sortedArray = parallelMergeSort(arr, 1);
        System.out.println(Arrays.toString(sortedArray));
    }

    public static int[] parallelMergeSort(int[] arr, int numThreads) {
        int[] scratch = new int[arr.length];
        ForkJoinPool pool = new ForkJoinPool(numThreads); // Set the number of threads
        pool.invoke(new MergeSortTask(arr, scratch, 0, arr.length));
        pool.shutdown();
        return arr;
    }
    
    static class MergeSortTask extends RecursiveTask<Void> {
        private int[] input;
        private int[] scratch;
        private int start;
        private int end;

        public MergeSortTask(int[] input, int[] scratch, int start, int end) {
            this.input = input;
            this.scratch = scratch;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Void compute() {
            if (end - start <= 1) {
                return null;
            }

            int middle = start + (end - start) / 2;

            MergeSortTask leftTask = new MergeSortTask(input, scratch, start, middle);
            MergeSortTask rightTask = new MergeSortTask(input, scratch, middle, end);

            invokeAll(leftTask, rightTask);

            merge(input, scratch, start, middle, end);
            System.arraycopy(scratch, start, input, start, end - start);

            return null;
        }

        private void merge(int[] input, int[] scratch, int start, int middle, int end) {
            int i = start;
            int j = middle;

            for (int k = start; k < end; k++) {
                if (i < middle && (j >= end || input[i] <= input[j])) {
                    scratch[k] = input[i];
                    i++;
                } else {
                    scratch[k] = input[j];
                    j++;
                }
            }
        }
    }
    public static void merge2(int[] input, int[] scratch, int start, int middle, int end) {
        int i = start;
        int j = middle;

        for (int k = start; k < end; k++) {
            if (i < middle && (j >= end || input[i] <= input[j])) {
                scratch[k] = input[i];
                i++;
            } else {
                scratch[k] = input[j];
                j++;
            }
        }
    }
}

