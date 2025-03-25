import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class trialbyfire {
    public static int[] parallelMerge(int[] arr1, int[] arr2, int[] scratch, int numThreads) {
        int[] combined = new int[arr1.length + arr2.length];
        ParallelMergeTask task = new ParallelMergeTask(arr1, arr2, combined, scratch, 0, 0, arr1.length, arr2.length, numThreads);
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        pool.invoke(task);
        return combined;
    }

    static class ParallelMergeTask extends RecursiveTask<Void> {
        private int[] arr1;
        private int[] arr2;
        private int[] combined;
        private int[] scratch;
        private int start1, start2, length1, length2;
        private int numThreads;

        ParallelMergeTask(int[] arr1, int[] arr2, int[] combined, int[] scratch, int start1, int start2, int length1, int length2, int numThreads) {
            this.arr1 = arr1;
            this.arr2 = arr2;
            this.combined = combined;
            this.scratch = scratch;
            this.start1 = start1;
            this.start2 = start2;
            this.length1 = length1;
            this.length2 = length2;
            this.numThreads = numThreads;
        }

        @Override
        protected Void compute() {
            if (length1 + length2 <= 10 || numThreads == 1) {
                mergeSequentially();
            } else {
                int mid1 = length1 / 2;
                int mid2 = binarySearch(arr2, start2, start2 + length2, arr1[start1 + mid1]);

                int midCombined = start1 + mid1 + start2 + mid2;
                combined[midCombined] = arr1[start1 + mid1];

                ParallelMergeTask leftTask = new ParallelMergeTask(arr1, arr2, combined, scratch, start1, start2, mid1, mid2, numThreads / 2);
                ParallelMergeTask rightTask = new ParallelMergeTask(arr1, arr2, combined, scratch, start1 + mid1 + 1, start2 + mid2, length1 - mid1 - 1, length2 - mid2, numThreads - numThreads / 2);

                leftTask.fork();
                rightTask.compute();
                leftTask.join();
            }
            return null;
        }

        private void mergeSequentially() {
            int i = start1;
            int j = start2;
            int k = start1 + start2;
            while (i < start1 + length1 && j < start2 + length2) {
                if (arr1[i] < arr2[j]) {
                    combined[k++] = arr1[i++];
                } else {
                    combined[k++] = arr2[j++];
                }
            }
            while (i < start1 + length1) {
                combined[k++] = arr1[i++];
            }
            while (j < start2 + length2) {
                combined[k++] = arr2[j++];
            }
        }

        private int binarySearch(int[] arr, int start, int end, int key) {
            int low = start;
            int high = end - 1;
            while (low <= high) {
                int mid = (low + high) >>> 1;
                int midVal = arr[mid];
                if (midVal < key) {
                    low = mid + 1;
                } else if (midVal > key) {
                    high = mid - 1;
                } else {
                    return mid - start;
                }
            }
            return low - start;
        }
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 5, 9, 12};
        int[] arr2 = {2, 4, 11, 17};
        int[] combined = parallelMerge(arr1, arr2, new int[arr1.length + arr2.length], 8);

        for (int num : combined) {
            System.out.print(num + " ");
        }
    }
}
