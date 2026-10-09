import java.util.Arrays;

public class week_9_Assignment_problems_1 {
    // Prefix sums allow each inclusive range query to be answered in O(1).
    public static long[] footfallReport(int[] visitors, int[][] queries) {
        if (visitors == null || visitors.length == 0) {
            throw new IllegalArgumentException("visitors must not be empty");
        }

        long[] prefix = new long[visitors.length + 1];
        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        long[] result = new long[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            if (start < 0 || end < start || end >= visitors.length) {
                throw new IllegalArgumentException("Invalid query: [" + start + ", " + end + "]");
            }
            result[i] = prefix[end + 1] - prefix[start];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};
        System.out.println("Footfall report: " + Arrays.toString(footfallReport(visitors, queries)));
        System.out.println("Naive time: O(q * n) worst case; optimized time: O(n + q); extra space: O(n + q).");
        System.out.println("Prefix preprocessing is worthwhile when many queries use the same visitors array.");
    }
}
