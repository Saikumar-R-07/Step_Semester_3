class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expaq10_5 {

    static Node removeKthFromEnd(Node head, int k) {
        Node dummy = new Node(0);      // sits before the head
        dummy.next = head;

        Node lead = dummy;
        Node trail = dummy;

        // Move lead k + 1 steps ahead of trail
        for (int i = 0; i <= k; i++) {
            lead = lead.next;
        }

        // Move both until lead falls off the end
        while (lead != null) {
            lead = lead.next;
            trail = trail.next;
        }

        // trail is now just before the node to remove
        trail.next = trail.next.next;

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
        StringBuilder sb = new StringBuilder();
        for (Node cur = head; cur != null; cur = cur.next) {
            sb.append(cur.data);
            if (cur.next != null) sb.append(" -> ");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        // Sample 1
        Node orders1 = build(10, 20, 30, 40, 50);
        print(removeKthFromEnd(orders1, 2));   // 10 -> 20 -> 30 -> 50

        // Sample 2
        Node orders2 = build(10, 20, 30, 40, 50);
        print(removeKthFromEnd(orders2, 5));   // 20 -> 30 -> 40 -> 50
    }
}
