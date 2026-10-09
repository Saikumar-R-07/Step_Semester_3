import java.util.*;

// Question 5: The Campus Canteen Smart Card
public class week_8_Assaignment_problems_5 {
    interface PricingPlan {
        double price(double originalPrice);
        String name();
    }
    static class DayScholarPlan implements PricingPlan {
        public double price(double p) { return p; }
        public String name() { return "Day Scholar"; }
    }
    static class HostellerPlan implements PricingPlan {
        public double price(double p) { return p * 0.90; }
        public String name() { return "Hosteller"; }
    }
    static class StaffPlan implements PricingPlan {
        public double price(double p) { return p * 0.80; }
        public String name() { return "Staff"; }
    }

    enum TransactionType { TOP_UP, PURCHASE, REFUND }

    static class Transaction {
        final TransactionType type;
        final double amount;
        final String description;
        Transaction(TransactionType type, double amount, String description) {
            this.type = type; this.amount = amount; this.description = description;
        }
        double signedAmount() { return type == TransactionType.PURCHASE ? -amount : amount; }
    }

    static class Purchase {
        final String item;
        final double charged;
        boolean refunded;
        Purchase(String item, double charged) { this.item = item; this.charged = charged; }
    }

    static class SmartCard {
        final String cardId;
        final PricingPlan plan;
        private double balance = 0;
        private boolean blocked = false;
        private final List<Transaction> transactions = new ArrayList<>();
        private final Map<String, Purchase> purchases = new LinkedHashMap<>();

        SmartCard(String cardId, PricingPlan plan) {
            this.cardId = cardId; this.plan = plan;
        }
        double getBalance() { return balance; }
        boolean isBlocked() { return blocked; }
        void block() { blocked = true; System.out.println(cardId + " blocked."); }
        void unblock() { blocked = false; System.out.println(cardId + " unblocked."); }

        boolean topUp(double amount) {
            if (blocked) {
                System.out.println("Top-up failed: Card is blocked.");
                return false;
            }
            if (amount < 100) {
                System.out.println("Top-up failed: Minimum top-up is ₹100.00.");
                return false;
            }
            if (balance + amount > 5000) {
                System.out.println("Top-up failed: Maximum balance is ₹5,000.00.");
                return false;
            }
            balance += amount;
            transactions.add(new Transaction(TransactionType.TOP_UP, amount, "Top-up"));
            System.out.printf(Locale.US, "%s topped up with ₹%.2f.%n", cardId, amount);
            System.out.printf(Locale.US, "Balance: ₹%.2f.%n", balance);
            return true;
        }

        boolean purchase(String item, double originalPrice) {
            if (blocked) {
                System.out.println("Purchase failed: Card is blocked.");
                return false;
            }
            if (originalPrice < 0 || purchases.containsKey(item)) {
                System.out.println("Purchase failed: Invalid price or item purchase ID already exists.");
                return false;
            }
            double charged = round(plan.price(originalPrice));
            if (balance < charged) {
                System.out.printf(Locale.US, "Purchase failed: Insufficient balance (required ₹%.2f, available ₹%.2f).%n",
                        charged, balance);
                return false;
            }
            balance -= charged;
            purchases.put(item, new Purchase(item, charged));
            transactions.add(new Transaction(TransactionType.PURCHASE, charged, item));
            System.out.printf(Locale.US, "%s purchased for ₹%.2f.%n", item, charged);
            System.out.printf(Locale.US, "Balance: ₹%.2f.%n", balance);
            return true;
        }

        boolean refund(String item) {
            Purchase p = purchases.get(item);
            if (p == null) {
                System.out.println("Refund rejected: No purchase found for " + item + ".");
                return false;
            }
            if (p.refunded) {
                System.out.println("Refund rejected: " + item + " has already been refunded.");
                return false;
            }
            if (balance + p.charged > 5000) {
                System.out.println("Refund rejected: Refund would exceed the maximum balance.");
                return false;
            }
            p.refunded = true;
            balance += p.charged;
            transactions.add(new Transaction(TransactionType.REFUND, p.charged, "Refund: " + item));
            System.out.printf(Locale.US, "Refund of ₹%.2f for %s processed.%n", p.charged, item);
            System.out.printf(Locale.US, "Balance: ₹%.2f.%n", balance);
            return true;
        }

        void miniStatement() {
            StringJoiner joiner = new StringJoiner(", ");
            for (Transaction t : transactions) {
                joiner.add(String.format(Locale.US, "%+.2f", t.signedAmount()));
            }
            System.out.println("Mini-statement for " + cardId + ": " + joiner + " = "
                    + String.format(Locale.US, "₹%.2f", balance) + ".");
        }
        private static double round(double n) { return Math.round(n * 100.0) / 100.0; }
    }

    public static void main(String[] args) {
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(500);
        card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Other Items", 400);
        card.refund("Veg Thali");
        card.refund("Veg Thali");
        card.miniStatement();
    }
}
