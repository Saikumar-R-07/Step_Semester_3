import java.util.Arrays;

// Problem 1: Pair Sum in a Sorted Array
public class week_9_Practice_problems_1 {
    // Returns the first matching pair found by the two-pointer technique.
    // Returns null when no pair exists.
    public static int[] pairSumSorted(int[] nums, int target) {
        if (nums == null || nums.length < 2) return null;

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            long sum = (long) nums[left] + nums[right];
            if (sum == target) {
                return new int[] { nums[left], nums[right] };
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums1 = {-4, -1, 0, 3, 5, 9};
        int target1 = 4;
        int[] result1 = pairSumSorted(nums1, target1);
        System.out.println(result1 == null ? "Not Found" : Arrays.toString(result1));

        int[] nums2 = {1, 2, 3};
        int[] result2 = pairSumSorted(nums2, 100);
        System.out.println(result2 == null ? "Not Found" : Arrays.toString(result2));

        System.out.println("Time Complexity: O(n)");
        System.out.println("Auxiliary Space Complexity: O(1), excluding returned pair.");
    }
}
