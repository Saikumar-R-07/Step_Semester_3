class Node {
    int coeff, exp;
    Node next;
    Node(int coeff, int exp) { this.coeff = coeff; this.exp = exp; }
}

public class expaq_10_1 {

    static Node addPolynomials(Node p, Node q) {
        Node dummy = new Node(0, 0);
        Node tail = dummy;

        while (p != null && q != null) {
            if (p.exp > q.exp) {
                tail = append(tail, p.coeff, p.exp);
                p = p.next;
            } else if (q.exp > p.exp) {
                tail = append(tail, q.coeff, q.exp);
                q = q.next;
            } else {
                int sum = p.coeff + q.coeff;
                if (sum != 0) tail = append(tail, sum, p.exp);  // drop zero terms
                p = p.next;
                q = q.next;
            }
        }

        // Copy whichever polynomial still has terms
        for (Node r = (p != null) ? p : q; r != null; r = r.next) {
            tail = append(tail, r.coeff, r.exp);
        }

        return dummy.next;
    }

    static Node
