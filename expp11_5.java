class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expp11_5 {

    static void reorderList(Node head) {
        if (head == null || head.next == null) return;

        // 1. Find the middle (slow ends at end of first half)
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Split and reverse the second half
        Node second = reverse(slow.next);
        slow.next = null;

        // 3. Merge alternately: first, second, first, second, ...
        Node first = head;
        while (second != null) {
            Node f = first.next;
            Node s = second.next;

            first.next = second;
            second.next = f;

            first = f;
            second = s;
        }
    }

    static Node reverse(Node head) {
        Node prev = null;
        while (head != null) {
            Node next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    static Node build(int... v) {
        Node dummy = new Node(0), t = dummy;
        for (int x : v) { t.next = new Node(x); t = t.next; }
        return dummy.next;
    }

    static void print(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) {
            sb.append(c.data);
            if (c.next != null) sb.append(" -> ");
        }
        System.out.println(sb);
    }

    public static void main(String[] args) {
        Node l1 = build(1, 2, 3, 4);
        reorderList(l1);
        print(l1);   // 1 -> 4 -> 2 -> 3

        Node l2 = build(1, 2, 3, 4, 5);
        reorderList(l2);
        print(l2);   // 1 -> 5 -> 2 -> 4 -> 3
    }
}
