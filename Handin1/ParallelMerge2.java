import java.util.Arrays;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ParallelMerge2 {

    public static int[] parallelMerge(int[] arr1, int p1, int r1, int p2, int r2, int p3, int numThreads) {
        int[] merged = new int[arr1.length];

        ForkJoinPool pool = new ForkJoinPool(numThreads);

        pool.invoke(new MergeTask2(arr1, p1, r1, p2, r2, merged, p3));

        return merged;
    }

    static class MergeTask2 extends RecursiveTask<Void> {
        private int[] T;
        private int p1;
        private int r1;
        private int p2;
        private int r2;
        private int[] A;
        private int p3; 

        public MergeTask2(int[] T,  int p1, int r1, int p2, int r2, int[] A, int p3) {
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
                int q1 = (int) Math.floor((p1+r1)/2); // = 1 
                int q2 = this.binarySearch(T[q1], T, p2,r2);  // = 4
                int q3 = p3 + (q1 - p1) + (q2 - p2);
                A[q3] = T[q1];

                MergeTask2 leftTask = new MergeTask2(T, p1, q1-1, p2, q2-1, A, p3);
                MergeTask2 rightTask = new MergeTask2(T, q1+1, r1, q2, r2, A, q3+1);

                invokeAll(leftTask, rightTask);

                return null;
            } 
        }

        private int binarySearch(int x, int[] T, int p, int r) {
            int low = p;
            int high = Math.max(p, r+1);
            while (low < high) {
                int mid = (int) Math.floor((low+high)/2);
                if (x <= T[mid]) {
                    high = mid;
                } else {
                    low = mid +1;
                }
            }
            return high;
        }
    }

    // public static void main(String[] args) {
        // int[] first = {1, 3, 5, 7};
        // int[] second = {2, 4, 6, 8};
        // int[] both = Arrays.copyOf(first, first.length + second.length);
        // System.arraycopy(second, 0, both, first.length, second.length);
        // int numThreads = 2;

        // int[] result = parallelMerge(both, numThreads);
        // System.out.println(Arrays.toString(result));
    // }
}
