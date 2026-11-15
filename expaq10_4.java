class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expaq10_4 {

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        // 1. Find the end of the first half (slow ends at middle / first-half tail)
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        Node secondHalf = reverse(slow.next);

        // 3. Compare the first half with the reversed second half
        boolean result = true;
        Node p1 = head, p2 = secondHalf;
        while (p2 != null) {
            if (p1.data != p2.data) {
                result = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // 4. Restore the list by reversing the second half again
        slow.next = reverse(secondHalf);

        return result;
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
        Node c1 = build(1, 2, 3, 2, 1);
        System.out.println(isPalindrome(c1));      // true

        Node c2 = build(1, 2, 3, 2);
        System.out.println(isPalindrome(c2));      // false

        Node c3 = build(1, 2, 2, 1);
        System.out.println(isPalindrome(c3));      // true
        print(c3);                                 // 1 -> 2 -> 2 -> 1 (restored)
    }
}
