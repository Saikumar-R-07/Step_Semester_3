class Job {
    final String name;
    int remaining;
    Job(String name, int remaining) { this.name = name; this.remaining = remaining; }
}

// Circular-array queue (no java.util collections)
class JobQueue {
    private final Job[] data;
    private int head = 0, size = 0;

    JobQueue(int capacity) { data = new Job[capacity]; }

    void enqueue(Job j) {
        data[(head + size) % data.length] = j;
        size++;
    }

    Job dequeue() {
        Job j = data[head];
        head = (head + 1) % data.length;
        size--;
        return j;
    }

    boolean isEmpty() { return size == 0; }
}

public class expp12_3 {

    static void run(int quantum, String[] names, int[] times) {
        int n = names.length;
        JobQueue q = new JobQueue(n);               // never holds more than n jobs
        for (int i = 0; i < n; i++) q.enqueue(new Job(names[i], times[i]));

        StringBuilder slices = new StringBuilder();
        StringBuilder finish = new StringBuilder("finish ");
        boolean firstFinish = true;
        int time = 0;

        while (!q.isEmpty()) {
            Job j = q.dequeue();
            int run = Math.min(quantum, j.remaining);

            if (slices.length() > 0) slices.append(" ");
            slices.append(j.name).append("(").append(time).append("-")
                  .append(time + run).append(")");

            time += run;
            j.remaining -= run;

            if (j.remaining > 0) {
                q.enqueue(j);                       // back of the line
            } else {
                if (!firstFinish) finish.append(", ");
                finish.append(j.name).append(" ").append(time);
                firstFinish = false;
            }
        }
        System.out.println(slices);
        System.out.println(finish);
    }

    public static void main(String[] args) {
        run(3, new String[]{"P1", "P2", "P3"}, new int[]{5, 3, 7});
        // P1(0-3) P2(3-6) P3(6-9) P1(9-11) P3(11-14) P3(14-15)
        // finish P2 6, P1 11, P3 15
    }
}
