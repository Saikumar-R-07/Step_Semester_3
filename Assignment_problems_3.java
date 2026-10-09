import java.util.Arrays;

public class Assignment_problems_3 {
    static class RaceEntry {
        protected String bibNumber;
        protected double entryFee;
        protected double amountPaid;

        private final double[] lateFeeHistory = new double[10];
        private int lateFeeCount = 0;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number must contain at least 4 non-blank characters.");
            }
            if (entryFee <= 0) throw new IllegalArgumentException("Entry fee must be positive.");
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
        }

        public void pay(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Payment must be positive.");
            amountPaid += amount;
        }

        public double getBalanceDue() {
            return Math.max(0.0, entryFee - amountPaid);
        }

        protected void applyLateFee(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Late fee must be positive.");
            if (lateFeeCount >= lateFeeHistory.length) {
                throw new IllegalStateException("Maximum of 10 late fees reached.");
            }
            entryFee += amount;
            lateFeeHistory[lateFeeCount++] = amount;
        }

        public double[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, lateFeeCount);
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.pay(30);
        runner.applyLateFee(20);

        System.out.println(runner.getBalanceDue());
        double[] history = runner.getLateFeeHistory();
        System.out.println(Arrays.toString(history));

        history[0] = 999;
        System.out.println(Arrays.toString(runner.getLateFeeHistory()));
    }
}
