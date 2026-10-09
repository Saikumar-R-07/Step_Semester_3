// Problem 3: Library Catalog Lookup (binary search)
public class week_9_Practice_problems_3 {
    static class Book {
        final String isbn;
        final String title;
        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    // Catalog must be sorted by ISBN in ascending lexicographical order.
    public static String findBook(Book[] catalog, String targetIsbn) {
        if (catalog == null || targetIsbn == null) return "Not Found";

        int low = 0;
        int high = catalog.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = catalog[mid].isbn.compareTo(targetIsbn);
            if (comparison == 0) return catalog[mid].title;
            if (comparison < 0) low = mid + 1;
            else high = mid - 1;
        }
        return "Not Found";
    }

    public static void main(String[] args) {
        Book[] catalog = {
            new Book("0001112223", "Introduction to Algebra"),
            new Book("0002223334", "Beginning Python"),
            new Book("0003334445", "Classic Mythology"),
            new Book("0004445556", "Data and Society"),
            new Book("0005556667", "European History")
        };

        System.out.println(findBook(catalog, "0003334445"));
        System.out.println(findBook(catalog, "0009998887"));
        System.out.println("Time Complexity per query: O(log n)");
        System.out.println("Auxiliary Space Complexity: O(1).");
        System.out.println("Binary search is suitable because the catalog is sorted and queries are frequent.");
    }
}
