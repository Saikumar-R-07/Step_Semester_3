class Node {
    int data;
    Node next;
    Node(int data) { this.data = data; }
}

public class expp11_3 {

    static Node addNumbers(Node a, Node b) {
        Node dummy = new Node(0), tail = dummy;
        int carry = 0;

        while (a != null || b != null || carry != 0) {
            int sum = carry;
            if (a != null) { sum += a.data; a = a.next; }
            if (b != null) { sum += b.data; b = b.next; }

            tail.next = new Node(sum % 10);
            tail = tail.next;
            carry = sum / 10;
        }
        return dummy.next;
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
        print(addNumbers(build(2, 4, 3), build(5, 6, 4)));  // 7 -> 0 -> 8
        print(addNumbers(build(9, 9), build(1)));           // 0 -> 0 -> 1
    }
}
