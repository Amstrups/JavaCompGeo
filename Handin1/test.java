import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class test {
    public static void main(String[] args) {
        int[] arr = { 12, 11, 13, 6, 5, 7 };
        int numThreads = 2;

        int[] res = parallelMergeSort(arr, numThreads);
        System.out.println("Sorted array: " + Arrays.toString(res));
    }

    public static int[] parallelMergeSort(int[] arr, int numThreads) {
        int scratch[] = new int[arr.length];
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        pool.invoke(new MergeSortTask(arr, 0, arr.length -1, scratch, 0));
        pool.shutdown();
        return scratch;
    }

    static class MergeSortTask extends RecursiveTask<Void> {
        private int[] arr;
        private int low;
        private int high;
        private int[] scratch;
        private int offset;

        MergeSortTask(int[] arr, int low, int high, int[] scratch, int offset) {
            this.arr = arr;
            this.low = low;
            this.high = high;
            this.scratch = scratch;
            this.offset = offset;
        }

        @Override
        protected Void compute() {
            int n = high - low+1;
            if (n == 1) {
                scratch[offset] = arr[low];
            } 
            // if (n < 10) {
            //     System.arraycopy(arr, low, scratch, 0, n);
            //     Arrays.sort(scratch);
            // }
            else {
                int[] T = new int[n+1];

                int mid = (int) Math.floor((low + high) / 2);
                int mid_ = mid - low + 1;

                invokeAll(new MergeSortTask(arr, low, mid, T, 1),new MergeSortTask(arr, mid+1, high, T, mid_+1));

                // MergeSortTask leftTask = new MergeSortTask(arr, low, mid, T, 1);
                // MergeSortTask rightTask = new MergeSortTask(arr, mid+1, high, T, mid_+1);

                // leftTask.fork();
                // rightTask.compute();

                // leftTask.join();

                MergeTask mergeTask = new MergeTask(T, 1, mid_, mid_ + 1, n, scratch, offset);
                mergeTask.invoke();
            }
            return null;
        }
    }

    static void merge(int[] arr, int low, int mid, int high, int[] scratch) {
        System.arraycopy(arr, low, scratch, low, high - low + 1);

        int i = low, j = mid + 1, k = low;

        while (i <= mid && j <= high) {
            if (scratch[i] <= scratch[j]) {
                arr[k++] = scratch[i++];
            } else {
                arr[k++] = scratch[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = scratch[i++];
        }
    }

    static class MergeTask extends RecursiveTask<Void> {
        private int[] T;
        private int p1;
        private int r1;
        private int p2;
        private int r2;
        private int[] A;
        private int p3;

        public MergeTask(int[] T, int p1, int r1, int p2, int r2, int[] A, int p3) {
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

                MergeTask leftTask = new MergeTask(T, p1, q1 - 1, p2, q2 - 1, A, p3);
                MergeTask rightTask = new MergeTask(T, q1 + 1, r1, q2, r2, A, q3 + 1);
                
                leftTask.fork();
                rightTask.compute();

                leftTask.join();

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
