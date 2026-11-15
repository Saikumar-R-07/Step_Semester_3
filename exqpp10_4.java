public class expp10_4 {

    // first index with prices[i] >= target
    static int lowerBound(int[] a, int target) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] >= target) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    // first index with prices[i] > target
    static int upperBound(int[] a, int target) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (a[mid] > target) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }

    public static void main(String[] args) {
        int[] prices = {100, 150, 150, 200, 300, 450};
        int low = 150, high = 300;

        int lb = lowerBound(prices, low);
        int ub = upperBound(prices, high);

        System.out.println("Lower bound index " + lb);
        System.out.println("Upper bound index " + ub);
        System.out.println("Count " + (ub - lb));
    }
}
