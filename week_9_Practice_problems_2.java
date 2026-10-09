// Problem 2: Warehouse Bin Grid Scan
public class week_9_Practice_problems_2 {
    static class WarehouseSummary {
        long total;
        int maxRow;
        int maxCol;
        int maxValue;

        WarehouseSummary(long total, int maxRow, int maxCol, int maxValue) {
            this.total = total;
            this.maxRow = maxRow;
            this.maxCol = maxCol;
            this.maxValue = maxValue;
        }
    }

    public static WarehouseSummary warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) {
            throw new IllegalArgumentException("Grid must not be empty.");
        }

        int columns = grid[0].length;
        long total = 0;
        int maxValue = -1;
        int maxRow = 0;
        int maxCol = 0;

        for (int r = 0; r < grid.length; r++) {
            if (grid[r] == null || grid[r].length != columns) {
                throw new IllegalArgumentException("Grid must be rectangular.");
            }
            for (int c = 0; c < columns; c++) {
                int value = grid[r][c];
                if (value < 0) throw new IllegalArgumentException("Bin counts must be non-negative.");
                total += value;
                // Strictly greater preserves the first maximum encountered in row-major order.
                if (value > maxValue) {
                    maxValue = value;
                    maxRow = r;
                    maxCol = c;
                }
            }
        }
        return new WarehouseSummary(total, maxRow, maxCol, maxValue);
    }

    public static void main(String[] args) {
        int[][] grid = {
            {4, 9, 2},
            {7, 1, 6},
            {3, 12, 5}
        };
        WarehouseSummary result = warehouseSummary(grid);
        System.out.println("total = " + result.total
                + ", maxCoordinate = (" + result.maxRow + ", " + result.maxCol + ")");
        System.out.println("Maximum bin count = " + result.maxValue);
        System.out.println("Time Complexity: O(m * n)");
        System.out.println("Auxiliary Space Complexity: O(1).");
    }
}
