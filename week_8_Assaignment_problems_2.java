import java.util.*;

// Question 2: The SwiftShip Parcel Tracker
public class week_8_Assaignment_problems_2 {
    interface ShippingType {
        double charge(double weightKg);
        String name();
    }
    static class StandardShipping implements ShippingType {
        public double charge(double w) { return 40 + 10 * w; }
        public String name() { return "Standard"; }
    }
    static class ExpressShipping implements ShippingType {
        public double charge(double w) { return 80 + 15 * w; }
        public String name() { return "Express"; }
    }
    static class FragileShipping implements ShippingType {
        private final ShippingType base = new StandardShipping();
        public double charge(double w) { return base.charge(w) + 50; }
        public String name() { return "Fragile"; }
    }

    interface NotificationChannel {
        void notify(String parcelId, ParcelStatus status);
    }
    static class SmsChannel implements NotificationChannel {
        public void notify(String id, ParcelStatus status) {
            System.out.println("[SMS] " + id + " is now " + status + ".");
        }
    }
    static class EmailChannel implements NotificationChannel {
        public void notify(String id, ParcelStatus status) {
            System.out.println("[Email] " + id + " is now " + status + ".");
        }
    }

    enum ParcelStatus { BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED }

    static class Customer {
        final String name;
        Customer(String name) { this.name = name; }
    }

    static class Parcel {
        final String id;
        final Customer customer;
        final double weight;
        final ShippingType shippingType;
        final List<NotificationChannel> channels = new ArrayList<>();
        private ParcelStatus status = ParcelStatus.BOOKED;

        Parcel(String id, Customer customer, double weight, ShippingType shippingType,
               List<NotificationChannel> channels) {
            if (weight <= 0) throw new IllegalArgumentException("Weight must be positive.");
            this.id = id; this.customer = customer; this.weight = weight;
            this.shippingType = shippingType;
            this.channels.addAll(channels);
        }
        ParcelStatus getStatus() { return status; }
        double getCharge() { return shippingType.charge(weight); }

        boolean advanceTo(ParcelStatus next) {
            ParcelStatus expected;
            switch (status) {
                case BOOKED: expected = ParcelStatus.PICKED_UP; break;
                case PICKED_UP: expected = ParcelStatus.IN_TRANSIT; break;
                case IN_TRANSIT: expected = ParcelStatus.OUT_FOR_DELIVERY; break;
                case OUT_FOR_DELIVERY: expected = ParcelStatus.DELIVERED; break;
                default:
                    System.out.println("Invalid transition: " + status + " → " + next + " is not allowed.");
                    return false;
            }
            if (next != expected) {
                System.out.println("Invalid transition: " + status + " → " + next + " is not allowed.");
                return false;
            }
            status = next;
            notifyChannels();
            return true;
        }
        boolean cancel() {
            if (status != ParcelStatus.BOOKED) {
                System.out.println("Cancellation failed: " + id + " can be cancelled only while BOOKED.");
                return false;
            }
            status = ParcelStatus.CANCELLED;
            notifyChannels();
            return true;
        }
        private void notifyChannels() {
            for (NotificationChannel channel : channels) channel.notify(id, status);
        }
    }

    static class ParcelService {
        Parcel book(String id, Customer customer, double weight, ShippingType type,
                    NotificationChannel... channels) {
            Parcel p = new Parcel(id, customer, weight, type, Arrays.asList(channels));
            System.out.printf(Locale.US, "Parcel %s booked (%s, %.0f kg).%n", id, type.name(), weight);
            System.out.printf(Locale.US, "Charge: ₹%.2f.%n", p.getCharge());
            // Notify subscribers of the initial BOOKED state.
            for (NotificationChannel channel : p.channels) channel.notify(id, p.getStatus());
            return p;
        }
    }

    public static void main(String[] args) {
        ParcelService service = new ParcelService();
        Customer customer = new Customer("Customer 1");
        Parcel p = service.book("P101", customer, 2, new ExpressShipping(),
                new SmsChannel(), new EmailChannel());
        p.advanceTo(ParcelStatus.PICKED_UP);
        p.cancel();
        p.advanceTo(ParcelStatus.IN_TRANSIT);
        p.advanceTo(ParcelStatus.DELIVERED);
    }
}
