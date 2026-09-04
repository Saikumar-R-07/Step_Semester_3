public class BusTicketAccount {
    private final String bookingId;
    private final double ticketFare;
    static {
        System.out.println("Reconciliation System Initialized.");
    }
    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }
    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }
    public final double calculatePenalty(int minutesLate) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0);
        return calc.calculatePenalty(this.ticketFare, minutesLate);
    }
    public void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        // Single account processing step
    }
    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) return;
        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double totalPenalties = 0.0;
        int minLength = Math.min(accounts.length, Math.min(amounts.length, minutesLateArray.length));
        for (int i = 0; i < minLength; i++) {
            BusTicketAccount acc = accounts[i];
            if (acc == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (acc instanceof SleeperBusTicketAccount) {
                sleeperCount++;
            } else {
                regularCount++;
            }

            totalPenalties += acc.calculatePenalty(minutesLateArray[i]);
        }
        System.out.println(processed + " processed | " + nullSkipped + " null skipped | " +
                           sleeperCount + " sleeper | " + regularCount + " regular | grand total penalties = " + totalPenalties);
    }
}
class SleeperBusTicketAccount extends BusTicketAccount {
    public SleeperBusTicketAccount(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }
}