public class expap11_5 {

    static int[][] exitDistance(char[][] plan) {
        int rows = plan.length, cols = plan[0].length;
        int[][] dist = new int[rows][cols];

        // Array-based queue: each cell enters the queue at most once
        int[] queue = new int[rows * cols];
        int head = 0, tail = 0;

        // Multi-source BFS: every exit starts at distance 0
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (plan[r][c] == 'E') {
                    dist[r][c] = 0;
                    queue[tail++] = r * cols + c;     // encode (r, c) as one int
                } else {
                    dist[r][c] = -1;                  // walls and unvisited rooms
                }
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (head < tail) {
            int cell = queue[head++];
            int r = cell / cols, c = cell % cols;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                if (plan[nr][nc] != '.' || dist[nr][nc] != -1) continue;  // wall or already reached

                dist[nr][nc] = dist[r][c] + 1;        // first visit = shortest distance
                queue[tail++] = nr * cols + nc;
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        char[][] plan = {
            {'.', '#', '.', 'E'},
            {'.', '.', '.', '#'},
            {'E', '#', '.', '.'}
        };

        int[][] result = exitDistance(plan);
        for (int[] row : result) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < row.length; i++) {
                sb.append(row[i]);
                if (i < row.length - 1) sb.append(" ");
            }
            System.out.println(sb);
        }
        // 2 -1 1 0
        // 1 2 2 -1
        // 0 -1 3 4
    }
}
