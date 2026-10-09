import java.util.HashMap;
import java.util.Map;

public class week_9_Assignment_problems_3 {
    // Counts contiguous subarrays whose sum is exactly k, including negative values.
    public static long countPeriods(int[] transactions, long k) {
        if (transactions == null || transactions.length == 0) {
            throw new IllegalArgumentException("transactions must not be empty");
        }

        Map<Long, Long> frequency = new HashMap<>();
        frequency.put(0L, 1L);
        long prefixSum = 0;
        long count = 0;

        for (int value : transactions) {
            prefixSum += value;
            count += frequency.getOrDefault(prefixSum - k, 0L);
            frequency.put(prefixSum, frequency.getOrDefault(prefixSum, 0L) + 1L);
        }
        return count;
    }

    public static void main(String[] args) {
        int[] transactions = {3, 4, -7, 1, 3, 3, 1, -4};
        System.out.println("Count for k=7: " + countPeriods(transactions, 7));
        System.out.println("Count for k=10: " + countPeriods(new int[]{1, 2, 3}, 10));
        System.out.println("Optimized expected time: O(n); extra space: O(n).");
        System.out.println("A brute-force method that recomputes each subarray sum can take O(n^3).");
    }
}
