class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expap12_5 {

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;   // empty or single node

        // 1. Find the end of the first half
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        Node second = reverse(slow.next);

        // 3. Compare the two halves node by node
        boolean result = true;
        for (Node p = head, q = second; q != null; p = p.next, q = q.next) {
            if (p.data != q.data) {
                result = false;
                break;
            }
        }

        // 4. Reverse the second half again and reattach it
        slow.next = reverse(second);

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
        for (Node c = head; c != null; c = c.next) sb.append(c.data).append(" -> ");
        System.out.println(sb.append("null"));
    }

    static void check(Node head) {
        System.out.println("Palindrome: " + isPalindrome(head));
        System.out.println();
        System.out.print("List after check: ");
        print(head);
        System.out.println();
    }

    public static void main(String[] args) {
        check(build(4, 7, 9, 7, 4));   // true
        check(build(4, 7, 7, 9));      // false
    }
}
