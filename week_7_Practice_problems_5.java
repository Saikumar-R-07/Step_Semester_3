public class week_7_Practice_problems_5 {
    interface Renewable {
        String renew();
    }

    interface Reservable {
        String reserve();
    }

    static abstract class LibraryItem {
        private static int counter = 1000;
        private final String itemId;
        protected final String title;

        public LibraryItem(String title) {
            if (title == null || title.trim().isEmpty())
                throw new IllegalArgumentException("Title cannot be blank.");
            this.title = title;
            counter++;
            itemId = "LIB-" + counter;
        }

        public String getItemId() {
            return itemId;
        }

        public abstract int getLoanPeriodDays();
    }

    static class Textbook extends LibraryItem implements Renewable, Reservable {
        public Textbook(String title) {
            super(title);
        }

        @Override
        public int getLoanPeriodDays() {
            return 14;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }

        @Override
        public String reserve() {
            return title + " reserved";
        }
    }

    static class Magazine extends LibraryItem implements Renewable {
        public Magazine(String title) {
            super(title);
        }

        @Override
        public int getLoanPeriodDays() {
            return 7;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }
    }

    static class DigitalPass implements Renewable {
        private final String resourceName;

        public DigitalPass(String resourceName) {
            if (resourceName == null || resourceName.trim().isEmpty())
                throw new IllegalArgumentException("Resource name cannot be blank.");
            this.resourceName = resourceName;
        }

        @Override
        public String renew() {
            return resourceName + " renewed";
        }
    }

    public static void processCheckouts(LibraryItem[] items) {
        for (LibraryItem item : items) {
            System.out.println(item.getItemId() + " | " + item.title
                    + " | Loan period: " + item.getLoanPeriodDays() + " days");
        }
    }

    public static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            return ((Reservable) o).reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook textbook = new Textbook("Java Fundamentals");
        Magazine magazine = new Magazine("Tech Monthly");
        DigitalPass digitalPass = new DigitalPass("E-Journal Access");

        System.out.println(textbook.getLoanPeriodDays());
        System.out.println(textbook.renew());
        System.out.println(textbook.reserve());

        System.out.println(reserveIfSupported(magazine));
        System.out.println(reserveIfSupported(digitalPass));

        // Upcasting: Textbook stored as its LibraryItem parent type.
        LibraryItem ref = textbook;
        System.out.println(reserveIfSupported(ref));

        processCheckouts(new LibraryItem[]{textbook, magazine});
    }
}
