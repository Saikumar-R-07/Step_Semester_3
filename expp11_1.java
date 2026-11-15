class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expp11_1 {

    static Node removeNthFromEnd(Node head, int n) {
        Node dummy = new Node(0);      // handles removal of the real head
        dummy.next = head;

        Node fast = dummy, slow = dummy;

        // Move fast n + 1 steps ahead so the gap is n nodes after slow
        for (int i = 0; i <= n; i++) fast = fast.next;

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;    // slow is just before the target
        return dummy.next;
    }

    static Node build(int... v) {
        Node dummy = new Node(0), t = dummy;
        for (int x : v) { t.next = new Node(x); t = t.next; }
        return dummy.next;
    }

    static void print(Node head) {
        if (head == null) { System.out.println("(empty list)"); return; }
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) {
            sb.append(c.data);
            if (c.next != null) sb.append(" -> ");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        print(removeNthFromEnd(build(1, 2, 3, 4, 5), 2));  // 1 -> 2 -> 3 -> 5
        print(removeNthFromEnd(build(1), 1));              // (empty list)
    }
}
