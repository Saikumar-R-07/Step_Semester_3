class BookIssue {
    private String title;
    private String borrowerName;
    private int daysOverdue;

    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    // Instance method: depends on THIS object's own daysOverdue value.
    public double fineAmount() {
        return daysOverdue > 0 ? daysOverdue * 5 : 0;
    }

    public boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    // totalFineCollected is static because it operates on a whole ARRAY of
    // BookIssue objects at once -- it belongs to the class as a concept
    // (a "library-wide" operation), not to any single book's state.
    // fineAmount() is instance-level because each book has its own
    // daysOverdue, so the fine must be computed per-object.
    public static double totalFineCollected(BookIssue[] issues) {
        double total = 0;
        for (BookIssue issue : issues) {
            total += issue.fineAmount();
        }
        return total;
    }

    public String getTitle() {
        return title;
    }

    public int getDaysOverdue() {
        return daysOverdue;
    }
}

public class F1 {
    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Alice", 18),
            new BookIssue("Effective Java", "Bob", 5),
            new BookIssue("Refactoring", "Carol", 0),
            new BookIssue("DSA Handbook", "Dev", 21),
            new BookIssue("Design Patterns", "Eve", 9)
        };

        for (BookIssue b : issues) {
            String status = b.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(b.getTitle() + " - " + b.getDaysOverdue() + " days - " + status);
        }

        System.out.println("Total fine collected: Rs " + BookIssue.totalFineCollected(issues));
    }
}
