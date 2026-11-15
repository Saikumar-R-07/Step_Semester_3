import java.util.HashMap;

class Node {
    int key;
    String page;
    Node prev, next;
    Node(int key, String page) { this.key = key; this.page = page; }
}

class RecentCache {
    private final int capacity;
    private final HashMap<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, null);   // dummy: most recent side
    private final Node tail = new Node(0, null);   // dummy: least recent side

    // Result of the last put, used only for printing
    int evictedKey = -1;
    boolean updated = false;

    RecentCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    String get(int key) {
        Node n = map.get(key);
        if (n == null) return "-1";
        unlink(n);
        addAfterHead(n);                 // now most recently used
        return n.page;
    }

    void put(int key, String page) {
        evictedKey = -1;
        updated = false;

        Node n = map.get(key);
        if (n != null) {                 // update existing page
            n.page = page;
            unlink(n);
            addAfterHead(n);
            updated = true;
            return;
        }

        if (map.size() == capacity) {    // full: drop the node just before tail
            Node lru = tail.prev;
            unlink(lru);
            map.remove(lru.key);
            evictedKey = lru.key;
        }

        Node fresh = new Node(key, page);
        map.put(key, fresh);
        addAfterHead(fresh);
    }

    private void unlink(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    private void addAfterHead(Node n) {
        n.next = head.next;
        n.prev = head;
        head.next.prev = n;
        head.next = n;
    }

    String contents() {
        StringBuilder sb = new StringBuilder();
        for (Node c = head.next; c != tail; c = c.next) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(c.key).append("=").append(c.page);
        }
        return sb.toString();
    }
}

public class expap12_3 {

    static void put(RecentCache c, int key, String page) {
        c.put(key, page);
        String line = "put(" + key + "," + page + ")";
        if (c.updated) line += " updated";
        else if (c.evictedKey != -1) line += " evicted " + c.evictedKey;
        System.out.println(line);
    }

    static void get(RecentCache c, int key) {
        System.out.println("get(" + key + ") -> " + c.get(key));
    }

    public static void main(String[] args) {
        RecentCache c = new RecentCache(2);
        put(c, 1, "Home");
        put(c, 2, "Notes");
        get(c, 1);
        put(c, 3, "Quiz");
        get(c, 2);
        put(c, 1, "Home2");
        put(c, 4, "Mail");
        get(c, 3);
        get(c, 1);
        System.out.println("Cache (most recent first): " + c.contents());
    }
}
