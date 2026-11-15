import java.io.*;
import java.util.StringTokenizer;

class Candidate {
    String name;
    int total;
    int climb;             // best climb
    int from, to;          // 1-based test numbers

    Candidate(String name, int[] s) {
        this.name = name;
        for (int x : s) total += x;

        // Largest sum over consecutive weekly changes (earliest run on ties)
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < s.length - 1; i++) {
            int sum = 0;
            for (int j = i + 1; j < s.length; j++) {
                sum += s[j] - s[j - 1];
                if (sum > best) {                   // strict > keeps earliest
                    best = sum;
                    from = i + 1;
                    to = j + 1;
                }
            }
        }
        climb = best;
    }
}

public class expp12_5 {

    // Stable merge sort, highest total first
    static void mergeSort(Candidate[] a, Candidate[] tmp, int lo, int hi) {
        if (hi - lo < 2) return;
        int mid = (lo + hi) >>> 1;
        mergeSort(a, tmp, lo, mid);
        mergeSort(a, tmp, mid, hi);

        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            // >= takes from the left on ties, so registration order is kept
            tmp[k++] = (a[i].total >= a[j].total) ? a[i++] : a[j++];
        }
        while (i < mid) tmp[k++] = a[i++];
        while (j < hi)  tmp[k++] = a[j++];
        for (k = lo; k < hi; k++) a[k] = tmp[k];
    }

    // Count of totals >= x in a descending array: index of first total < x
    static int countAtLeast(Candidate[] a, int x) {
        int lo = 0, hi = a.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a[mid].total >= x) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    // Two pointers on the descending list
    static String findPair(Candidate[] a, int t) {
        int i = 0, j = a.length - 1;
        while (i < j) {
            int sum = a[i].total + a[j].total;
            if (sum == t) return a[i].name + " + " + a[j].name;
            if (sum > t) i++;      // too big: take a smaller left value
            else j--;              // too small: take a larger right value
        }
        return "no pair";
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        for (String line; (line = br.readLine()) != null; ) all.append(line).append(' ');
        StringTokenizer st = new StringTokenizer(all.toString());

        int n = Integer.parseInt(st.nextToken());
        Candidate[] c = new Candidate[n];
        for (int i = 0; i < n; i++) {
            String name = st.nextToken();
            int[] s = new int[6];
            for (int k = 0; k < 6; k++) s[k] = Integer.parseInt(st.nextToken());
            c[i] = new Candidate(name, s);
        }

        mergeSort(c, new Candidate[n], 0, n);

        StringBuilder out = new StringBuilder("Shortlist:\n");
        for (int i = 0; i < n; i++) {
            Candidate x = c[i];
            out.append(i + 1).append(". ").append(x.name).append(" ").append(x.total)
               .append(" | best climb ").append(x.climb >= 0 ? "+" + x.climb : "" + x.climb)
               .append(" (test ").append(x.from).append(" to ").append(x.to).append(")\n");
        }

        while (st.hasMoreTokens()) {
            String cmd = st.nextToken();
            int v = Integer.parseInt(st.nextToken());
            if (cmd.equals("CUTOFF")) {
                out.append("CUTOFF ").append(v).append(": ")
                   .append(countAtLeast(c, v)).append(" candidates\n");
            } else {
                out.append("PAIR ").append(v).append(": ").append(findPair(c, v)).append("\n");
            }
        }
        System.out.print(out);
    }
}
