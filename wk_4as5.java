import java.util.Locale;
public class wk_4as5 {
    static class DeliveryAccount {
        protected String studentId;
        protected double orderValue;
        protected static double surgeRatePercentPerMinute;
        static {
            surgeRatePercentPerMinute = 1.0; // flat 1% of orderValue per minute delayed
        }
        public DeliveryAccount(String studentId, double orderValue) {
            this.studentId = studentId;
            this.orderValue = orderValue;
        }
        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }
        final double calculateSurgeFee(int delayMinutes) {
            if (delayMinutes <= 0) {
                return 0.0;
            }
            return orderValue * surgeRatePercentPerMinute * delayMinutes / 100.0;
        }
        public String getStudentId() {
            return studentId;
        }
    }
    static class PremiumDeliveryAccount extends DeliveryAccount {
        static final double PREMIUM_DISCOUNT = 0.5; // 50% off surge fees
        public PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }
        public PremiumDeliveryAccount(String studentId) {
            super(studentId);
        }
    }
    static class ReconciliationProcessor {
        private int processedCount = 0;
        private int nullSkippedCount = 0;
        private int premiumCount = 0;
        private int regularCount = 0;
        private double grandTotalSurgeFees = 0.0;
        void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
            if (account == null) {
                nullSkippedCount++;
                return;
            }
            try {
                account.orderValue = amount; // this run's order amount
                double fee = account.calculateSurgeFee(delayMinutes);
                if (account instanceof PremiumDeliveryAccount) {
                    fee = fee * (1 - PremiumDeliveryAccount.PREMIUM_DISCOUNT);
                    premiumCount++;
                } else {
                    regularCount++;
                }
                grandTotalSurgeFees += fee;
                processedCount++;
            } catch (Exception e) {
                // Defensive: one bad account never takes down the whole batch.
                System.out.println("Skipped account " + account.getStudentId() + " due to: " + e.getMessage());
            }
        }
        static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
            ReconciliationProcessor processor = new ReconciliationProcessor();
            int n = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));
            if (accounts.length != amounts.length || amounts.length != delayMinutesArray.length) {
                System.out.println("Warning: input arrays have mismatched lengths -- only the first "
                        + n + " entries will be processed.");
            }
            for (int i = 0; i < n; i++) {
                processor.processAccount(accounts[i], amounts[i], delayMinutesArray[i]);
            }
            System.out.println(processor.processedCount + " processed | "
                    + processor.nullSkippedCount + " null skipped | "
                    + processor.premiumCount + " premium | "
                    + processor.regularCount + " regular | "
                    + "grand total surge fees = "
                    + String.format(Locale.US, "%.2f", processor.grandTotalSurgeFees));
        }
    }
    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
                new PremiumDeliveryAccount("STU001", 500),
                null,
                new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};
        ReconciliationProcessor.processBatch(accounts, amounts, delayMinutesArray);
    }
}