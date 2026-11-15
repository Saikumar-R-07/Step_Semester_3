class Node {
    String title;
    int day;
    Node next;
    Node(String title, int day) { this.title = title; this.day = day; }
}

public class expap12_4 {

    static Node sortByDueDay(Node head) {
        if (head == null || head.next == null) return head;

        // 1. Find the end of the first half and cut the list there
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node right = slow.next;
        slow.next = null;                 // split into two lists

        // 2. Sort each half
        Node l = sortByDueDay(head);
        Node r = sortByDueDay(right);

        // 3. Merge by relinking
        return merge(l, r);
    }

    static Node merge(Node a, Node b) {
        Node dummy = new Node("", 0), tail = dummy;
        while (a != null && b != null) {
            if (a.day <= b.day) {         // <= takes the left on ties: stable
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    // Helpers
    static Node build(String... pairs) {   // each "title:day"
        Node dummy = new Node("", 0), tail = dummy;
        for (String p : pairs) {
            int idx = p.lastIndexOf(':');
            tail.next = new Node(p.substring(0, idx), Integer.parseInt(p.substring(idx + 1)));
            tail = tail.next;
        }
        return dummy.next;
    }

    static void print(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next)
            sb.append(c.title).append(":").append(c.day).append(" -> ");
        System.out.println(sb.append("null"));
    }

    public static void main(String[] args) {
        print(sortByDueDay(build("Java:15", "DSA:3", "OS:9", "DBMS:3", "CN:12", "AI:1")));
        // AI:1 -> DSA:3 -> DBMS:3 -> OS:9 -> CN:12 -> Java:15 -> null

        print(sortByDueDay(build()));
        // null
    }
}
