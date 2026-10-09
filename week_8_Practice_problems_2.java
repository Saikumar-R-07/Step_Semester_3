import java.util.*;

public class week_8_Practice_problems_2 {

    interface PricingStrategy {
        double calculateCharge(int days);
    }

    static class StandardPricing implements PricingStrategy {
        public double calculateCharge(int days) {
            return days * 50.0;
        }
    }

    static class LuxuryPricing implements PricingStrategy {
        public double calculateCharge(int days) {
            return days * 100.0;
        }
    }

    static class SUVPricing implements PricingStrategy {
        public double calculateCharge(int days) {
            return days * 75.0;
        }
    }

    static class Vehicle {
        private final String name;
        private final PricingStrategy pricing;
        private boolean available = true;

        Vehicle(String name, PricingStrategy pricing) {
            this.name = name;
            this.pricing = pricing;
        }

        String getName() { return name; }
        boolean isAvailable() { return available; }

        double rent(int days) {
            if (days <= 0) throw new IllegalArgumentException("Rental days must be positive.");
            if (!available) {
                System.out.println(name + " cannot be rented: already has an active rental.");
                return -1;
            }
            available = false;
            double charge = pricing.calculateCharge(days);
            System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                    name, days, charge);
            return charge;
        }

        void returnVehicle() {
            if (available) {
                System.out.println(name + " is already available.");
            } else {
                available = true;
                System.out.println(name + " returned. Now available.");
            }
        }
    }

    static class RentalService {
        private final List<Vehicle> vehicles = new ArrayList<>();

        void addVehicle(Vehicle vehicle) { vehicles.add(vehicle); }

        void rentVehicle(Vehicle vehicle, int days) {
            if (!vehicles.contains(vehicle)) {
                System.out.println("Vehicle is not registered with this rental service.");
                return;
            }
            vehicle.rent(days);
        }

        void returnVehicle(Vehicle vehicle) {
            if (vehicles.contains(vehicle)) vehicle.returnVehicle();
        }
    }

    public static void main(String[] args) {
        RentalService service = new RentalService();
        Vehicle luxury = new Vehicle("Luxury Car A", new LuxuryPricing());
        Vehicle standard = new Vehicle("Standard Car B", new StandardPricing());
        Vehicle suv = new Vehicle("SUV C", new SUVPricing());

        service.addVehicle(luxury);
        service.addVehicle(standard);
        service.addVehicle(suv);

        service.rentVehicle(luxury, 3);
        service.rentVehicle(standard, 5);
        service.returnVehicle(luxury);
        service.rentVehicle(suv, 2);
    }
}
