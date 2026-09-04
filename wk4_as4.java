public final class wk4_as4 {
    static final class SurgeFeeCalculator {
        private final double minimumSurgePercent;
        public SurgeFeeCalculator(double minimumSurgePercent) {
            this.minimumSurgePercent = minimumSurgePercent;
        }
        final double calculateSurgeFee(double orderValue, int delayMinutes) {
            if (orderValue < 0) {
                throw new IllegalArgumentException("orderValue cannot be negative.");
            }
            if (delayMinutes < 0) {
                throw new IllegalArgumentException("delayMinutes cannot be negative.");
            }
            if (delayMinutes == 0) {
                return 0.0; // on-time: never charged, floor included
            }
            int tier1 = Math.min(delayMinutes, 5);                 // minutes 1-5
            int tier2 = Math.max(Math.min(delayMinutes, 15) - 5, 0); // minutes 6-15
            int tier3 = Math.max(delayMinutes - 15, 0);              // minutes 16+
            double tieredPercent = (tier1 * 0.5) + (tier2 * 1.0) + (tier3 * 2.0);
            double tieredFee = orderValue * tieredPercent / 100.0;
            double floorFee = orderValue * minimumSurgePercent / 100.0;
            return Math.max(tieredFee, floorFee);
        }
    }
    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0); // 1% minimum surge floor
        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));  // Rs 0.0
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));  // Rs 5.0  (floor wins over Rs 2.5)
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16)); // Rs 72.5
    }
}