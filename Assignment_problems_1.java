public class Assignment_problems_1 {
    static class RaceEntry {
        protected String bibNumber;
        protected double entryFee;
        protected double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number must contain at least 4 non-blank characters.");
            }
            if (entryFee <= 0) {
                throw new IllegalArgumentException("Entry fee must be positive.");
            }
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
        }

        public void pay(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Payment must be positive.");
            }
            amountPaid += amount;
        }

        public double getBalanceDue() {
            return Math.max(0.0, entryFee - amountPaid);
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.pay(30);
        System.out.println(runner.getBalanceDue());

        String[] bibs = {"BIB1", "B1", "BIB2"};
        System.out.println(registerBatch(bibs, 80));
    }
}
