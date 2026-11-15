class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expaq10_3 {

    static Node mergeSorted(Node a, Node b) {
        Node dummy = new Node(0);   // the single allowed dummy node
        Node tail = dummy;

        while (a != null && b != null) {
            if (a.data <= b.data) {      // <= keeps the merge stable
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        // One list is exhausted; attach the rest of the other
        tail.next = (a != null) ? a : b;

        return dummy.next;
    }

    // Helpers
    static Node build(int... values) {
        Node dummy = new Node(0), tail = dummy;
        for (int v : values) {
            tail.next = new Node(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    static void print(Node head) {
        if (head == null) {
            System.out.println("(empty)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Node cur = head; cur != null; cur = cur.next) {
            sb.append(cur.data);
            if (cur.next != null) sb.append(" -> ");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        // Sample 1
        print(mergeSorted(build(3, 8, 15), build(5, 8, 12, 20)));
        // 3 -> 5 -> 8 -> 8 -> 12 -> 15 -> 20

        // Sample 2
        print(mergeSorted(null, build(4)));
        // 4
    }
}
