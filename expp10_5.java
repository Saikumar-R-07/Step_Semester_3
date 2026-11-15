import java.util.*;

interface Reservable { String reserve(String memberId); }
interface Downloadable { String download(); }

abstract class Resource {
    protected final String id;
    Resource(String id) { this.id = id; }
    String getId() { return id; }
}

class Book extends Resource implements Reservable {
    Book(String id) { super(id); }
    public String reserve(String m) { return id + " reserved for " + m; }
}

class EBook extends Resource implements Downloadable {
    EBook(String id) { super(id); }
    public String download() { return id + " downloaded"; }
}

class Library {
    private Resource[] items = new Resource[2];
    private int size = 0;

    // Binary search by ID; returns index, or -(insertion point) - 1
    private int search(String id) {
        int lo = 0, hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = items[mid].getId().compareTo(id);
            if (cmp == 0) return mid;
            if (cmp < 0) lo = mid + 1; else hi = mid - 1;
        }
        return -(lo + 1);
    }

    String add(Resource r) {
        int idx = search(r.getId());
        if (idx >= 0) return "duplicate rejected";
        int pos = -(idx + 1);
        if (size == items.length) items = Arrays.copyOf(items, size * 2);
        for (int i = size; i > pos; i--) items[i] = items[i - 1]; // keep sorted
        items[pos] = r;
        size++;
        return r.getId() + " added";
    }

    String reserve(String id, String member) {
        int idx = search(id);
        if (idx < 0) return id + " not found";
        Resource r = items[idx];
        return (r instanceof Reservable)
            ? ((Reservable) r).reserve(member)
            : id + " rejected: reserve unsupported";
    }

    String download(String id) {
        int idx = search(id);
        if (idx < 0) return id + " not found";
        Resource r = items[idx];
        return (r instanceof Downloadable)
            ? ((Downloadable) r).download()
            : id + " rejected: download unsupported";
    }

    String find(String id) {
        int idx = search(id);
        return idx >= 0 ? id + " found at index " + idx : id + " not found";
    }
}

public class expp10_5 {
    public static void main(String[] args) {
        Library lib = new Library();
        System.out.println(lib.add(new Book("B1")));
        System.out.println(lib.add(new Book("B1")));
        System.out.println(lib.add(new EBook("E1")));
        System.out.println(lib.reserve("B1", "M1"));
        System.out.println(lib.download("B1"));
        System.out.println(lib.download("E1"));
        System.out.println(lib.find("E1"));
    }
}
