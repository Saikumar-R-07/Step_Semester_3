public class Assignment_problems_2 {
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
        }

        public void pay(double amount) {
            if (amount <= 0) throw new IllegalArgumentException("Payment must be positive.");
            amountPaid += amount;
        }

        public double getBalanceDue() {
            return Math.max(0.0, entryFee - amountPaid);
        }

        public String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
        }
    }

    static class RunnerEntry extends RaceEntry {
        protected String category;

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

    static class EliteRunnerEntry extends RunnerEntry {
        private double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            if (sponsorBonus < 0) throw new IllegalArgumentException("Sponsor bonus cannot be negative.");
            this.sponsorBonus = sponsorBonus;
        }

        @Override
        public String announce() {
            return "Elite Runner | Bib: " + bibNumber + " | Category: " + category
                    + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
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

    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Single-inheritance descendant";
        }
        return "Base generation";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0.0;
        for (RaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry elite = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relay = new RelayTeamEntry("BIB4001", 300, 4);

        System.out.println(runner.announce());
        System.out.println(elite.announce());
        System.out.println(relay.announce());
        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(relay));

        RaceEntry[] entries = {runner, elite, relay};
        System.out.println(getTotalBalanceDue(entries));
    }
}
