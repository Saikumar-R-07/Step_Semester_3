public class BusTicket {
    private String passengerName;
    private String destination;
    private boolean checkedIn;
    // Only constructor that exists — you cannot make an empty BusTicket.
    public BusTicket(String passengerName, String destination) {
        if (!isValid(passengerName) || !isValid(destination)) {
            throw new IllegalArgumentException("Invalid booking");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }
    private static boolean isValid(String s) {
        if (s == null) {
            return false;
        }
        String trimmed = s.trim();
        if (trimmed.length() == 0) {
            return false;
        }
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            boolean isLetter = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            boolean isSpace = (c == ' ');
            if (!isLetter && !isSpace) {
                return false; // found a digit or symbol -> invalid
            }
        }
        return true;
    }
    public void markCheckedIn() {
        if (checkedIn) {
            throw new IllegalStateException("Already checked in!");
        }
        checkedIn = true;
    }
    public String getPassengerName() {
        return passengerName;
    }
    public String getDestination() {
        return destination;
    }
    public static void processBatch(String[][] rawBookings) {
        java.util.List<String> seen = new java.util.ArrayList<>();
        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        for (int i = 0; i < rawBookings.length; i++) {
            String name = rawBookings[i][0];
            String dest = rawBookings[i][1];
            try {
                BusTicket ticket = new BusTicket(name, dest);
                String key = ticket.getPassengerName().toLowerCase() + "_" + ticket.getDestination().toLowerCase();
                if (seen.contains(key)) {
                    duplicates++;
                } else {
                    seen.add(key);
                    valid++;
                }
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }
    public static void main(String[] args) {
        String[][] bookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };
        processBatch(bookings);
    }
}
