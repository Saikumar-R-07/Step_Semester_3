import java.util.ArrayList;
import java.util.List;

public class week_9_Assignment_problems_5 {
    // Returns all grid values in clockwise spiral order.
    public static List<Integer> auditRoute(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            throw new IllegalArgumentException("grid must not be empty");
        }

        int rows = grid.length;
        int cols = grid[0].length;
        for (int[] row : grid) {
            if (row == null || row.length != cols) {
                throw new IllegalArgumentException("grid must be rectangular");
            }
        }

        List<Integer> result = new ArrayList<>(rows * cols);
        int top = 0, bottom = rows - 1;
        int left = 0, right = cols - 1;

        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                result.add(grid[top][col]);
            }
            top++;

            for (int row = top; row <= bottom; row++) {
                result.add(grid[row][right]);
            }
            right--;

            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(grid[bottom][col]);
                }
                bottom--;
            }

            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(grid[row][left]);
                }
                left++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        System.out.println("Spiral audit route: " + auditRoute(grid));
        System.out.println("Time: O(m * n); auxiliary space excluding output: O(1).");
    }
}
