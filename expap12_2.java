class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expap12_2 {

    static Node reverseInGroups(Node head, int k) {
        Node dummy = new Node(0);          // handles the changing head
        dummy.next = head;
        Node prevTail = dummy;             // tail of the previous (already processed) group

        while (true) {
            // 1. Check that k nodes exist starting from prevTail.next
            Node check = prevTail.next;
            int count = 0;
            while (check != null && count < k) {
                check = check.next;
                count++;
            }
            if (count < k) break;          // final short group stays as it is

            // 2. Reverse exactly k nodes
            Node groupHead = prevTail.next;   // becomes the group's tail after reversal
            Node prev = check;                // first node after the group
            Node cur = groupHead;
            for (int i = 0; i < k; i++) {
                Node next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }

            // 3. Connect the previous group's tail to the new group head
            prevTail.next = prev;
            prevTail = groupHead;
        }
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
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(" -> ");
        System.out.println(sb.append("null"));
    }

    public static void main(String[] args) {
        print(reverseInGroups(build(1, 2, 3, 4, 5, 6, 7, 8), 3));
        // 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8 -> null

        print(reverseInGroups(build(11, 12), 3));
        // 11 -> 12 -> null

        print(reverseInGroups(null, 2));
        // null
    }
}
