class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expp11_2 {

    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        // 1. Find the end of the first half
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        Node second = reverse(slow.next);

        // 3. Compare the halves
        boolean ok = true;
        for (Node p = head, q = second; q != null; p = p.next, q = q.next) {
            if (p.data != q.data) { ok = false; break; }
        }

        // 4. Restore the list (optional, but good practice)
        slow.next = reverse(second);
        return ok;
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

    public static void main(String[] args) {
        System.out.println(isPalindrome(build(1, 2, 2, 1)));  // true
        System.out.println(isPalindrome(build(1, 2, 3)));     // false
    }
}
