public class week_7_Practice_problems_1 {
    static abstract class PaymentMethod {
        private static int counter = 1000;
        private final String transactionId;

        public PaymentMethod() {
            counter++;
            transactionId = "TXN-" + counter;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public abstract String processPayment(double amount);

        public String processPayment(double amount, String note) {
            return processPayment(amount) + " (" + note + ")";
        }
    }

    static class CreditCardPayment extends PaymentMethod {
        private final String cardNumberLastFour;

        public CreditCardPayment(String cardNumberLastFour) {
            if (cardNumberLastFour == null || !cardNumberLastFour.matches("\\d{4}")) {
                throw new IllegalArgumentException("Enter exactly four digits.");
            }
            this.cardNumberLastFour = cardNumberLastFour;
        }

        @Override
        public String processPayment(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
            return "Charged $" + amount + " to card ending " + cardNumberLastFour
                    + " - Txn " + getTransactionId();
        }
    }

    static class CashPayment extends PaymentMethod {
        public CashPayment() {
            super();
        }

        @Override
        public String processPayment(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive.");
            return "Received $" + amount + " in cash - Txn " + getTransactionId();
        }
    }

    public static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    public static void main(String[] args) {
        CreditCardPayment cc = new CreditCardPayment("4471");
        System.out.println(cc.processPayment(250.0));
        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        CashPayment cash = new CashPayment();
        System.out.println(cash.processPayment(40.0));

        // Upcasting: a CreditCardPayment reference is stored as PaymentMethod.
        PaymentMethod ref = cc;
        printConfirmation(ref, 250.0);

        // This does not compile because PaymentMethod is abstract:
        // PaymentMethod invalid = new PaymentMethod() { ... };
    }
}
