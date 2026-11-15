class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expp11_4 {

    static Node findIntersection(Node a, Node b) {
        int lenA = length(a), lenB = length(b);

        // Advance the longer list by the difference
        while (lenA > lenB) { a = a.next; lenA--; }
        while (lenB > lenA) { b = b.next; lenB--; }

        // Compare references (identity), not values
        while (a != b) {
            a = a.next;
            b = b.next;
        }
        return a;   // shared node, or null if the lists never meet
    }

    static int length(Node head) {
        int n = 0;
        for (Node c = head; c != null; c = c.next) n++;
        return n;
    }

    static Node build(int... v) {
        Node dummy = new Node(0), t = dummy;
        for (int x : v) { t.next = new Node(x); t = t.next; }
        return dummy.next;
    }

    public static void main(String[] args) {
        // Case 1: a = 4 -> 1 -> [8 -> 4 -> 5], b = 5 -> 6 -> 1 -> [8 -> 4 -> 5]
        Node shared = build(8, 4, 5);
        Node a = build(4, 1);
        Node b = build(5, 6, 1);
        tail(a).next = shared;
        tail(b).next = shared;

        Node r1 = findIntersection(a, b);
        System.out.println(r1 == null ? "null" : "Node with value " + r1.data);  // 8

        // Case 2: no shared node
        Node r2 = findIntersection(build(2, 6, 4), build(1, 5));
        System.out.println(r2 == null ? "null" : "Node with value " + r2.data);  // null
    }

    static Node tail(Node head) {
        while (head.next != null) head = head.next;
        return head;
    }
}
