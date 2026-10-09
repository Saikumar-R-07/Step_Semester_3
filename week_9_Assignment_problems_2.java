public class week_9_Assignment_problems_2 {
    // Returns {length, startIndex}; ties are resolved in favor of the earliest start.
    public static int[] longestStreak(int[] costs, long budget) {
        if (costs == null || costs.length == 0) {
            throw new IllegalArgumentException("costs must not be empty");
        }
        if (budget < 0) {
            throw new IllegalArgumentException("budget must be non-negative");
        }

        int left = 0, bestLength = 0, bestStart = -1;
        long sum = 0;

        for (int right = 0; right < costs.length; right++) {
            if (costs[right] < 0) {
                throw new IllegalArgumentException("All costs must be non-negative");
            }
            sum += costs[right];

            while (left <= right && sum > budget) {
                sum -= costs[left++];
            }

            int length = right - left + 1;
            if (length > bestLength) {
                bestLength = length;
                bestStart = left;
            }
        }
        return new int[]{bestLength, bestStart};
    }

    public static void main(String[] args) {
        int[] costs = {4, 2, 1, 7, 3, 1, 2, 1, 5};
        int[] answer = longestStreak(costs, 8);
        System.out.println("Longest streak: (" + answer[0] + ", " + answer[1] + ")");
        int[] answer2 = longestStreak(new int[]{9, 10}, 8);
        System.out.println("No affordable day: (" + answer2[0] + ", " + answer2[1] + ")");
        System.out.println("Optimized time: O(n); extra space: O(1). Brute-force checking all runs is O(n^2) or worse if each sum is recomputed.");
    }
}
