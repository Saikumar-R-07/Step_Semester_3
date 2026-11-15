public class expap11_2 {

    static String expandBanner(String code) {
        int n = code.length();

        // Array-based stacks (no recursion, no java.util collections)
        int[] counts = new int[n];
        StringBuilder[] texts = new StringBuilder[n];
        int top = -1;

        StringBuilder current = new StringBuilder();
        int k = 0;                                   // number being read

        for (int i = 0; i < n; i++) {
            char ch = code.charAt(i);

            if (Character.isDigit(ch)) {
                k = k * 10 + (ch - '0');             // supports multi-digit k
            } else if (ch == '[') {
                counts[++top] = k;                   // save repeat count
                texts[top] = current;                // save text built so far
                current = new StringBuilder();       // start a fresh builder
                k = 0;
            } else if (ch == ']') {
                int repeat = counts[top];
                StringBuilder outer = texts[top--];
                String inner = current.toString();
                for (int r = 0; r < repeat; r++) {
                    outer.append(inner);             // repeat inner text
                }
                current = outer;                     // continue building outer text
            } else {
                current.append(ch);                  // plain letter
            }
        }
        return current.toString();
    }

    public static void main(String[] args) {
        System.out.println(expandBanner("3[ab]2[c]"));    // abababcc
        System.out.println(expandBanner("2[x3[y]]z"));    // xyyyxyyyz
        System.out.println(expandBanner("10[a]"));        // aaaaaaaaaa (multi-digit k)
    }
}
