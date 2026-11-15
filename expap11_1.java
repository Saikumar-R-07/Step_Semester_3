import java.util.Arrays;

public class expap11_1 {

    static int[] priceStreak(int[] price) {
        int n = price.length;
        int[] streak = new int[n];

        // Array-based stack of indices (no java.util collections needed)
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {
            // Pop every earlier day whose price is <= today's price
            while (top >= 0 && price[stack[top]] <= price[i]) {
                top--;
            }

            // Distance back to the first day with a higher price
            streak[i] = (top == -1) ? i + 1 : i - stack[top];

            stack[++top] = i;   // today may block future streaks
        }
        return streak;
    }

    public static void main(String[] args) {
