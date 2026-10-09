public class Assignment_problems_4 {
    static class RaceEntry {
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
        }

        public double getBalanceDue() {
            return Math.max(0.0, entryFee - amountPaid);
        }

        public void pay(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Payment must be positive.");
            amountPaid += amount;
        }

        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
        }
    }

    static class RunnerEntry extends RaceEntry {
        private String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        public String announce() {
            return "Runner Entry | Bib: " + bibNumber + " | Category: " + category
                    + " | Balance: " + getBalanceDue();
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

        @Override
        public String announce() {
            return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize
                    + " | Balance: " + getBalanceDue();
        }
    }

    public static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();

        for (RaceEntry entry : entries) {
            if (report.length() > 0) report.append(" | ");

            // Polymorphic method call: each object supplies its own announcement.
            report.append(entry.announce());

            // Downcast only after confirming the object's actual type.
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
            }
        }
        return report.toString();
    }

    public static void main(String[] args) {
        RaceEntry[] entries = {
            new RunnerEntry("BIB2001", 90, "Open 10K"),
            new RelayTeamEntry("BIB4001", 300, 4)
        };

        System.out.println(announceAll(entries));
    }
}
