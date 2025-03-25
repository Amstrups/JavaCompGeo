import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FinalParallelMerge {
    public static final int numThreads = 1;

    public static void main(String[] args) {
        int[] arr = { 5, 1, 8, 4, 2, 7, 6, 3 };

        int[] sortedArray = parallelMergeSort(arr, numThreads);
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

            ParallelMerge.parallelMerge(input, start, middle - 1, middle, end, 0, numThreads);

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

    static class MergeTask2 extends RecursiveTask<Void> {
        private int[] T;
        private int p1;
        private int r1;
        private int p2;
        private int r2;
        private int[] A;
        private int p3;

        public MergeTask2(int[] T, int p1, int r1, int p2, int r2, int[] A, int p3) {
            this.T = T;
            this.p1 = p1;
            this.r1 = r1;
            this.p2 = p2;
            this.r2 = r2;
            this.A = A;
            this.p3 = p3;
        }

        @Override
        protected Void compute() {
            int n1 = this.r1 - this.p1 + 1;
            int n2 = this.r2 - this.p2 + 1;
            if (n1 < n2) {
                int p1_old = this.p1;
                int r1_old = this.r1;
                this.p1 = this.p2;
                this.p2 = p1_old;
                this.r1 = this.r2;
                this.r2 = r1_old;
                n1 = n2;
            }
            if (n1 == 0) {
                return null;
            } else {
                int q1 = (int) Math.floor((p1 + r1) / 2);
                int q2 = this.binarySearch(T[q1], T, p2, r2);
                int q3 = p3 + (q1 - p1) + (q2 - p2);
                A[q3] = T[q1];

                MergeTask2 leftTask = new MergeTask2(T, p1, q1 - 1, p2, q2 - 1, A, p3);
                MergeTask2 rightTask = new MergeTask2(T, q1 + 1, r1, q2, r2, A, q3 + 1);

                invokeAll(leftTask, rightTask);

                return null;
            }
        }

        private int binarySearch(int x, int[] T, int p, int r) {
            int low = p;
            int high = Math.max(p, r + 1);
            while (low < high) {
                int mid = (int) Math.floor((low + high) / 2);
                if (x <= T[mid]) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return high;
        }
    }

}
