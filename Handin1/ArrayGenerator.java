import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ArrayGenerator {
    public int[] sorted;
    public int[] array; 
    public int partitionIndex; 
    private int size;

    @Override
    public String toString() {
        return Arrays.toString(sorted) + "\n" + Arrays.toString(array);
    }
    public ArrayGenerator(int size) {
        this.partitionIndex = -1;
        this.size = size;
        sorted = new int[size];
        array = new int[size]; 

    } 
    public void SortedArray() {
        for (int i = 0; i < size; i++) {
            sorted[i] = i+1;
            array[i] = i+1;
        }
    }
    public void ReverseSortedArray() {
        for (int i = 0; i < size; i++) {
            array[i] = size-i;
            sorted[i] = i+1;
        }
    }

    public void PartitionedSortedArray() {
        int[] b = new int[(int) Math.floor(size/2)];

        int count = 0;
        int bCount = 0;        
        for (int i = 0; i < size; i++) {
            sorted[i] = i+1;
            if (i % 2 == 0) {
                array[count++] = i+1;
            } else {
                b[bCount++] = i+1;
            }
        }
        this.partitionIndex = count; 
        System.arraycopy(b, 0, array, count, bCount);
    }

        public void PartitionedSortedArray2() {
        int[] b = new int[(int) Math.floor(size/2)];

        int count = 0;
        int bCount = 0;        
        for (int i = 0; i < size; i++) {
            sorted[i] = i+1;
            if (i % 2 == 0) {
                array[count++] = i+1;
            } else if (i%5 == 0) {
                array[count++] = i+1;
            }else {
                b[bCount++] = i+1;
            }
        }
        this.partitionIndex = count; 
        System.arraycopy(b, 0, array, count, bCount);
    }

    public void NonZeroValues(int n, int seed) {
        int remainder = Math.min(n,size); 
        int sortedRemainder = n; 
        Random randomNum = new Random(seed);
        for (int i = 0; i < size; i++) {
            if (sortedRemainder > 0) {
                sorted[i] = 1;
                sortedRemainder--;
            }
            int next = randomNum.nextInt(10);
            if (remainder > (size - i - 1) ) {
                array[i] = 1;
                remainder--; 
            } else if (remainder > 0 && next == 1) {
                array[i] = 1;
                remainder--;
            } else {
                array[i] = 0;
            }
        }
    }
    public void NonZeroValues(int n) {
        NonZeroValues(n, 0);
    }

    public void RandomArray(int seed) {
        for (int i = 0; i < size; i++) {
            sorted[i] = i+1; 
        }

        array = Arrays.copyOf(sorted, sorted.length);
        Random rand = new Random(seed);
		
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			int temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
    }

    public void RandomArray() {
        RandomArray(0);
    }

    public static void main(String[] args) {
        ArrayGenerator a = new ArrayGenerator(20);
        Random r = new Random();

        a.NonZeroValues(6);
        System.out.println(a);
    }
}