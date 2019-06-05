package hw3.hash;

import java.util.Iterator;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] frequency = new int[M];
        int N = 0;
        Iterator iter = oomages.listIterator();
        while (iter.hasNext()) {
            N++;
            frequency[(iter.next().hashCode() & 0x7FFFFFFF) % M]++;
        }
        double min = (double)N / 50;
        double max = N / 2.5;
        for (int i = 0; i < M; i++) {
            if (frequency[i] < min || frequency[i] > max) {
                return false;
            }
        }
        return true;
    }
}
