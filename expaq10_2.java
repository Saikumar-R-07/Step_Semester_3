class Node {
    String data;          // URL (the problem's example uses int, but pages are strings)
    Node prev, next;
    Node(String data) { this.data = data; }
}

class BrowserHistory {
    private Node current;

    BrowserHistory(String homepage) {
        current = new Node(homepage);
    }

    // O(1): link new node after current, dropping all forward history
    void visit(String url) {
        Node node = new Node(url);
        node.prev = current;
        current.next = node;   // overwrites old forward chain
        current = node;
    }

    // O(steps): stop at the first page
    String back(int steps) {
        while (steps-- > 0 && current.prev != null) {
            current = current.prev;
        }
        return current.data;
    }

    // O(steps): stop at the last page
    String forward(int steps) {
        while (steps-- > 0 && current.next != null) {
            current = current.next;
        }
        return current.data;
    }
}

public class expaq10_2 {
    public static void main(String[] args) {
        BrowserHistory b = new BrowserHistory("srm.edu");
        b.visit("a.com");
        b.visit("b.com");
        b.visit("c.com");

        StringBuilder out = new StringBuilder();
        out.append(b.back(1)).append(", ");      // b.com
        out.append(b.back(1)).append(", ");      // a.com
        out.append(b.forward(1)).append(", ");   // b.com
        b.visit("d.com");
        out.append("d.com").append(", ");        // visit's current page
        out.append(b.forward(2)).append(", ");   // d.com (no forward pages)
        out.append(b.back(2)).append(", ");      // a.com
        out.append(b.back(7));                   // srm.edu

        System.out.println(out);
    }
}
