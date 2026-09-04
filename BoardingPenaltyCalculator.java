public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent; // locked once set
    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }
    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Values cannot be negative");
        }
        if (minutesLate == 0) {
            return 0.0; // on time, no penalty at all
        }
        double percent = 0;
        // Minutes 1 to 5 -> 0.5% each
        int minutesInBand1 = Math.min(minutesLate, 5);
        percent = percent + (minutesInBand1 * 0.5);
        // Minutes 6 to 15 -> 1% each
        if (minutesLate > 5) {
            int minutesInBand2 = Math.min(minutesLate, 15) - 5;
            percent = percent + (minutesInBand2 * 1.0);
        }
        // Minute 16 onward -> 2% each
        if (minutesLate > 15) {
            int minutesInBand3 = minutesLate - 15;
            percent = percent + (minutesInBand3 * 2.0);
        }
        double tieredPenalty = (percent / 100.0) * ticketFare;
        double floorPenalty = (minimumPenaltyPercent / 100.0) * ticketFare;
        // Whichever is bigger, that's what gets charged
        double finalPenalty = Math.max(tieredPenalty, floorPenalty);
        return Math.round(finalPenalty * 100.0) / 100.0;
    }
    public static void main(String[] args) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0);
        System.out.println(calc.calculatePenalty(1000, 0));  // 0.0
        System.out.println(calc.calculatePenalty(1000, 1));  // 10.0
        System.out.println(calc.calculatePenalty(1000, 16)); // 145.0
    }
}
