public class FareSplitter {
    private String tripId;
    private double totalFare;
    private int passengerCount;
    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }
        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Need at least 1 passenger");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }
    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }
    public FareSplitter(String tripId) {
        this(tripId, 0.0);
    }
    public double[] fareBreakdown() {
        double[] shares = new double[passengerCount];
        long totalPaise = Math.round(totalFare * 100);
        long onePersonPaise = totalPaise / passengerCount;
        long paiseGivenOut = 0;
        for (int i = 0; i < passengerCount - 1; i++) {
            shares[i] = onePersonPaise / 100.0;
            paiseGivenOut = paiseGivenOut + onePersonPaise;
        }
        long lastPersonPaise = totalPaise - paiseGivenOut;
        shares[passengerCount - 1] = lastPersonPaise / 100.0;
        return shares;
    }
    public boolean isConfirmationOverdue(int confirmed, int expected) {
        if (confirmed < expected) {
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        FareSplitter f1 = new FareSplitter("TRIP001", 100000, 3);
        System.out.println(java.util.Arrays.toString(f1.fareBreakdown()));
        FareSplitter f2 = new FareSplitter("TRIP003");
        System.out.println(java.util.Arrays.toString(f2.fareBreakdown()));
    }
}
