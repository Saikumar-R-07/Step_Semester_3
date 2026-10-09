// Problem 4: Library Catalog Lookup (duplicate question in the source PDF)
public class week_9_Practice_problems_4 {
    static class Book {
        final String isbn;
        final String title;
        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    // Iterative binary search; ISBN strings retain leading zeroes.
    public static String findBook(Book[] catalog, String targetIsbn) {
        if (catalog == null || targetIsbn == null) return "Not Found";

        int left = 0;
        int right = catalog.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = catalog[mid].isbn.compareTo(targetIsbn);
            if (cmp == 0) return catalog[mid].title;
            if (cmp < 0) left = mid + 1;
            else right = mid - 1;
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
    }
}
