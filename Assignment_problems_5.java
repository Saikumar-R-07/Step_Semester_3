public class Assignment_problems_5 {
    static class RaceEntry {
        private static int bibCounter = 0;

        private final String entryCode;
        protected String bibNumber;
        protected double entryFee;
        protected double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < 4) {
                throw new IllegalArgumentException("Bib number must contain at least 4 non-blank characters.");
            }
            if (entryFee <= 0) throw new IllegalArgumentException("Entry fee must be positive.");

            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.amountPaid = 0.0;

            bibCounter++;
            this.entryCode = "ENTRY-" + (1000 + bibCounter);
        }

        public void pay(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Payment must be positive.");
            amountPaid += amount;
        }

        public void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        public double getBalanceDue() {
            return Math.max(0.0, entryFee - amountPaid);
        }

        public String getEntryCode() {
            return entryCode;
        }

        public static int getBibCounter() {
            return bibCounter;
        }

        public static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != 5) return false;
            if (code.charAt(0) != 'M') return false;

            for (int i = 1; i <= 3; i++) {
                if (!Character.isDigit(code.charAt(i))) return false;
            }

            char last = code.charAt(4);
            return Character.isUpperCase(last) && Character.isLetter(last);
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }
    }

    static class RelayTeamEntry extends RaceEntry {
        private int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            if (teamSize <= 0) throw new IllegalArgumentException("Team size must be positive.");
            this.teamSize = teamSize;
        }

        public int getTeamSize() {
            return teamSize;
        }
    }

    public static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (entry instanceof RelayTeamEntry) {
                relayCount++;
            } else {
                individualCount++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + relayCount + " relay | " + individualCount + " individual";
    }

    public static void main(String[] args) {
        RaceEntry entry = new RaceEntry("BIB5001", 50);
        System.out.println(entry.getEntryCode());
        System.out.println(RaceEntry.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));
        entry.pay(10, "UPI");

        RaceEntry[] entries = {
            new RunnerEntry("BIB3001", 150, "Elite Full Marathon"),
            null,
            new RelayTeamEntry("BIB4001", 300, 4)
        };

        System.out.println(settleNight(entries));
        System.out.println(RaceEntry.getBibCounter());
    }
}
