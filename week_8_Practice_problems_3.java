import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class week_8_Practice_problems_3 {

    interface RoomPricing {
        double pricePerNight();
    }

    static class StandardPricing implements RoomPricing {
        public double pricePerNight() { return 150.0; }
    }

    static class DeluxePricing implements RoomPricing {
        public double pricePerNight() { return 200.0; }
    }

    static class SuitePricing implements RoomPricing {
        public double pricePerNight() { return 350.0; }
    }

    static class Room {
        private final String roomName;
        private final RoomPricing pricing;
        private final List<Reservation> reservations = new ArrayList<>();

        Room(String roomName, RoomPricing pricing) {
            this.roomName = roomName;
            this.pricing = pricing;
        }

        boolean isAvailable(LocalDate start, LocalDate end) {
            for (Reservation r : reservations) {
                if (!r.isCancelled() && start.isBefore(r.getEndDate())
                        && end.isAfter(r.getStartDate())) {
                    return false;
                }
            }
            return true;
        }

        void addReservation(Reservation reservation) {
            reservations.add(reservation);
        }

        double calculatePrice(LocalDate start, LocalDate end) {
            return ChronoUnit.DAYS.between(start, end) * pricing.pricePerNight();
        }

        String getRoomName() { return roomName; }
    }

    static class Reservation {
        private final Room room;
        private final String customer;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final LocalDate cancellationDeadline;
        private boolean cancelled = false;

        Reservation(Room room, String customer, LocalDate startDate,
                    LocalDate endDate, LocalDate cancellationDeadline) {
            this.room = room;
            this.customer = customer;
            this.startDate = startDate;
            this.endDate = endDate;
            this.cancellationDeadline = cancellationDeadline;
        }

        boolean isCancelled() { return cancelled; }
        LocalDate getStartDate() { return startDate; }
        LocalDate getEndDate() { return endDate; }

        boolean cancel(LocalDate today) {
            if (cancelled || today.isAfter(cancellationDeadline)) {
                return false;
            }
            cancelled = true;
            return true;
        }
    }

    static class BookingManager {
        private final List<Reservation> reservations = new ArrayList<>();

        Reservation bookRoom(Room room, String customer, LocalDate start,
                             LocalDate end, LocalDate cancellationDeadline) {
            if (!start.isBefore(end)) {
                System.out.println("Booking failed: end date must be after start date.");
                return null;
            }
            if (!room.isAvailable(start, end)) {
                System.out.println("Booking failed: " + room.getRoomName()
                        + " is not available for " + start + " to " + end + ".");
                return null;
            }
            Reservation reservation = new Reservation(room, customer, start, end,
                    cancellationDeadline);
            room.addReservation(reservation);
            reservations.add(reservation);
            double price = room.calculatePrice(start, end);
            System.out.printf("%s booked from %s to %s. Total price: $%.2f%n",
                    room.getRoomName(), start, end, price);
            return reservation;
        }

        void cancelReservation(Reservation reservation, LocalDate today) {
            if (reservation != null && reservation.cancel(today)) {
                System.out.println("Reservation for room cancelled successfully.");
            } else {
                System.out.println("Cancellation failed: deadline passed or reservation already cancelled.");
            }
        }
    }

    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Room deluxe = new Room("Deluxe Room 101", new DeluxePricing());
        Room standard = new Room("Standard Room 205", new StandardPricing());

        LocalDate start1 = LocalDate.of(2024, 12, 1);
        LocalDate end1 = LocalDate.of(2024, 12, 5);
        LocalDate start2 = LocalDate.of(2024, 12, 3);
        LocalDate end2 = LocalDate.of(2024, 12, 7);

        Reservation r1 = manager.bookRoom(deluxe, "Customer A", start1, end1,
                LocalDate.of(2024, 11, 30));
        manager.bookRoom(standard, "Customer B", start2, end2,
                LocalDate.of(2024, 12, 2));
        manager.bookRoom(deluxe, "Customer C", start2, end2,
                LocalDate.of(2024, 12, 2));
        manager.cancelReservation(r1, LocalDate.of(2024, 11, 29));
    }
}
