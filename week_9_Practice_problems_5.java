// Problem 5: Maximum Sum Subarray of Fixed Size K
public class week_9_Practice_problems_5 {
    public static long maxSumSubarray(int[] sales, int k) {
        if (sales == null || k < 1 || k > sales.length) {
            throw new IllegalArgumentException("Require 1 <= k <= sales.length.");
        }

        long windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        long maxSum = windowSum;
        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i];       // Add the new rightmost value.
            windowSum -= sales[i - k];   // Remove the value leaving the window.
            if (windowSum > maxSum) maxSum = windowSum;
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] sales = {2, 1, 5, 1, 3, 2};
        int k = 3;
        System.out.println(maxSumSubarray(sales, k));
        System.out.println("Naive Time Complexity: O(n * k)");
        System.out.println("Optimized Time Complexity: O(n)");
        System.out.println("Auxiliary Space Complexity: O(1).");
    }
}
