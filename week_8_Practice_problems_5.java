import java.util.*;

public class week_8_Practice_problems_5 {

    interface IPaymentMethod {
        String getName();
        boolean pay(double amount);
    }

    static class CreditCardPayment implements IPaymentMethod {
        private final boolean simulateSuccess;

        CreditCardPayment(boolean simulateSuccess) {
            this.simulateSuccess = simulateSuccess;
        }

        public String getName() { return "Credit Card"; }
        public boolean pay(double amount) { return simulateSuccess; }
    }

    static class DigitalWalletPayment implements IPaymentMethod {
        private final boolean simulateSuccess;

        DigitalWalletPayment(boolean simulateSuccess) {
            this.simulateSuccess = simulateSuccess;
        }

        public String getName() { return "Digital Wallet"; }
        public boolean pay(double amount) { return simulateSuccess; }
    }

    static class FoodItem {
        private final String name;
        private final double price;

        FoodItem(String name, double price) {
            if (name == null || name.trim().isEmpty() || price < 0) {
                throw new IllegalArgumentException("Invalid food item.");
            }
            this.name = name;
            this.price = price;
        }

        String getName() { return name; }
        double getPrice() { return price; }
    }

    static class LineItem {
        private final FoodItem item;
        private final int quantity;

        LineItem(FoodItem item, int quantity) {
            if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive.");
            this.item = item;
            this.quantity = quantity;
        }

        double subtotal() { return item.getPrice() * quantity; }
        String describe() { return item.getName() + " (Qty " + quantity + ")"; }
    }

    static class Order {
        private final int orderId;
        private final List<LineItem> items = new ArrayList<>();
        private String status = "Created";
        private double total;

        Order(int orderId) {
            this.orderId = orderId;
            System.out.println("Order created.");
        }

        void addItem(FoodItem item, int quantity) {
            if (!"Created".equals(status)) {
                throw new IllegalStateException("Items cannot be added after placement.");
            }
            LineItem line = new LineItem(item, quantity);
            items.add(line);
            System.out.println("Added " + line.describe() + ".");
        }

        boolean place(IPaymentMethod paymentMethod) {
            if (items.isEmpty()) {
                System.out.println("Cannot place order: Order must contain at least one item.");
                return false;
            }
            if (!"Created".equals(status)) {
                System.out.println("Order has already been placed.");
                return false;
            }

            total = 0;
            for (LineItem item : items) total += item.subtotal();
            status = "Pending Payment";
            System.out.println("Order placed successfully.");

            boolean success = paymentMethod.pay(total);
            if (success) {
                status = "Paid";
                System.out.println("Payment via " + paymentMethod.getName() + " successful.");
                System.out.println("Order status: " + status + ".");
                System.out.println("Notification: Order #" + orderId + " placed and paid.");
            } else {
                System.out.println("Payment via " + paymentMethod.getName() + " failed.");
                System.out.println("Order status: " + status + ".");
                System.out.println("Notification: Order #" + orderId + " placed, awaiting payment.");
            }
            return success;
        }
    }

    static class Customer {
        private final String name;
        private final List<Order> orders = new ArrayList<>();

        Customer(String name) { this.name = name; }

        Order createOrder(int id) {
            Order order = new Order(id);
            orders.add(order);
            return order;
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Alex");

        Order order1 = customer.createOrder(123);
        order1.addItem(new FoodItem("Pizza", 12.0), 2);
        order1.addItem(new FoodItem("Soda", 2.0), 1);

        Order emptyOrder = customer.createOrder(125);
        emptyOrder.place(new CreditCardPayment(true));

        order1.place(new CreditCardPayment(true));

        Order order2 = customer.createOrder(124);
        order2.addItem(new FoodItem("Burger", 8.0), 1);
        order2.place(new DigitalWalletPayment(false));
    }
}
