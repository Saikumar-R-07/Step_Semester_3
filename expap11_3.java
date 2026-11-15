public class expap11_3 {

    static int evaluate(String expr) {
        int n = expr.length();

        // Array-based stacks (no java.util collections needed)
        int[] values = new int[n];
        char[] ops = new char[n];
        int vTop = -1, oTop = -1;

        for (int i = 0; i < n; i++) {
            char ch = expr.charAt(i);

            if (ch == ' ') continue;

            if (Character.isDigit(ch)) {
                int num = 0;
                while (i < n && Character.isDigit(expr.charAt(i))) {
                    num = num * 10 + (expr.charAt(i) - '0');   // multi-digit numbers
                    i++;
                }
                i--;                                           // for-loop will advance again
                values[++vTop] = num;
            } else if (ch == '(') {
                ops[++oTop] = ch;
            } else if (ch == ')') {
                while (ops[oTop] != '(') {
                    vTop = applyTop(values, vTop, ops[oTop--]);
                }
                oTop--;                                        // discard '('
            } else {                                           // + - * /
                while (oTop >= 0 && ops[oTop] != '('
                        && precedence(ops[oTop]) >= precedence(ch)) {
                    vTop = applyTop(values, vTop, ops[oTop--]);
                }
                ops[++oTop] = ch;
            }
        }

        // Apply whatever operators remain
        while (oTop >= 0) {
            vTop = applyTop(values, vTop, ops[oTop--]);
        }
        return values[0];
    }

    static int precedence(char op) {
        return (op == '*' || op == '/') ? 2 : 1;
    }

    // Pops two operands, applies op, pushes the result; returns the new top index
    static int applyTop(int[] values, int vTop, char op) {
        int b = values[vTop--];
        int a = values[vTop--];
        int r;
        switch (op) {
            case '+': r = a + b; break;
            case '-': r = a - b; break;
            case '*': r = a * b; break;
            default:  r = a / b; break;                        // integer division
        }
        values[++vTop] = r;
        return vTop;
    }

    public static void main(String[] args) {
        System.out.println(evaluate("2 * (3 + 4) - 10 / 5"));   // 12
        System.out.println(evaluate("(8 - 2) * (1 + 2) / 4"));  // 4
    }
}
