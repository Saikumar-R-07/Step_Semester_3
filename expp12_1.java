public class expp12_1 {

    // Static nested: needs no Order object, can exist on its own
    static class LineItem {
        final String productName;
        final int quantity;
        final double unitPrice;

        LineItem(String productName, int quantity, double unitPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        double total() { return quantity * unitPrice; }
    }

    private LineItem[] items = new LineItem[4];
    private int size = 0;

    void add(String product, int qty, double price) {
        if (size == items.length) {                       // grow when full
            LineItem[] bigger = new LineItem[size * 2];
            for (int i = 0; i < size; i++) bigger[i] = items[i];
            items = bigger;
        }
        items[size++] = new LineItem(product, qty, price);
    }

    // Member inner class: tied to one Order, reads its private array
    class Cursor {
        private int pos = 0;
        boolean hasNext() { return pos < size; }
        LineItem next()   { return items[pos++]; }
        void reset()      { pos = 0; }
    }

    Cursor cursor() { return new Cursor(); }

    static String fmt(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }

    public static void main(String[] args) {
        Order order = new Order();
        order.add("Pen", 3, 10);
        order.add("Book", 2, 250);
        order.add("Bag", 1, 700);

        double total = 0;
        Order.Cursor c = order.cursor();
        while (c.hasNext()) {
            LineItem li = c.next();
            System.out.println(li.productName + " " + fmt(li.total()));
            total += li.total();
        }
        System.out.println("Total " + fmt(total));
        System.out.println("cursor: inner (reads the order's array)");
        System.out.println("line item: static nested (needs no order)");
    }
}
