class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expap12_1 {

    static String detectLoop(Node head) {
        Node slow = head, fast = head;

        // Phase 1: slow moves 1, fast moves 2
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        if (fast == null || fast.next == null) return "Loop: no";

        // Phase 2: walk once round the loop to count its length
        int length = 1;
        for (Node cur = slow.next; cur != slow; cur = cur.next) length++;

        // Phase 3: reset one pointer to head; both move 1 step until they meet
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return "Loop: yes, starts at stop " + slow.data + ", length " + length;
    }

    // Builds the list and links the last node back to index pos (-1 = no loop)
    static Node build(int pos, int... values) {
        Node dummy = new Node(0), tail = dummy;
        Node loopTarget = null;
        for (int i = 0; i < values.length; i++) {
            tail.next = new Node(values[i]);
            tail = tail.next;
            if (i == pos) loopTarget = tail;
        }
        if (pos >= 0) tail.next = loopTarget;
        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println(detectLoop(build(2, 10, 20, 30, 40, 50)));
        // Loop: yes, starts at stop 30, length 3
        System.out.println(detectLoop(build(-1, 10, 20, 30)));
        // Loop: no
        System.out.println(detectLoop(build(0, 7)));
        // Loop: yes, starts at stop 7, length 1
    }
}
