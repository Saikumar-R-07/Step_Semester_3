abstract class Ride {
    protected final double distance;
    Ride(double distance) { this.distance = distance; }
    abstract double calculateFare();
    abstract String type();
}

class Auto extends Ride {
    Auto(double d) { super(d); }
    double calculateFare() { return 30 + 15 * distance; }
    String type() { return "Auto"; }
}

class Cab extends Ride {
    private final double surge;
    Cab(double d, double surge) { super(d); this.surge = surge; }
    double calculateFare() { return (50 + 20 * distance) * surge; }  // surge after base
    String type() { return "Cab"; }
}

class Bike extends Ride {
    private final int waitingMinutes;
    Bike(double d, int waiting) { super(d); this.waitingMinutes = waiting; }
    double calculateFare() { return 20 + 10 * distance + 2 * waitingMinutes; }
    String type() { return "Bike"; }
}

public class expp12_2 {

    static String fmt(double v) {
        return v == (long) v ? String.valueOf((long) v) : String.valueOf(v);
    }

    public static void main(String[] args) {
        Ride[] rides = {
            new Auto(4),
            new Cab(6, 1.5),
            new Bike(3, 5)
        };

        double total = 0;
        for (Ride r : rides) {                       // one loop prices every ride
            double fare = r.calculateFare();         // runtime polymorphism
            System.out.println(r.type() + ": " + fmt(fare));
            total += fare;
        }
        System.out.println("Total: " + fmt(total));
    }
}
