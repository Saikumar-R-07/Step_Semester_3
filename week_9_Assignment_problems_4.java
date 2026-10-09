public class week_9_Assignment_problems_4 {
    // First index whose value is >= target.
    private static int lowerBound(int[] scores, int target) {
        int left = 0, right = scores.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (scores[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // First index whose value is > target.
    private static int upperBound(int[] scores, int target) {
        int left = 0, right = scores.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (scores[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public static int countInBand(int[] scores, int low, int high) {
        if (scores == null) {
            throw new IllegalArgumentException("scores must not be null");
        }
        if (low > high) {
            return 0;
        }
        return upperBound(scores, high) - lowerBound(scores, low);
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        System.out.println("Count in [42, 58]: " + countInBand(scores, 42, 58));
        System.out.println("Count in [90, 100]: " + countInBand(scores, 90, 100));
        System.out.println("Linear scan: O(n) time, O(1) extra space.");
        System.out.println("Binary-search solution: O(log n) time, O(1) extra space.");
    }
}
