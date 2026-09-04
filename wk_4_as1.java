public class wk_4_as1 {
    static class FoodOrder {
        private final String studentName;
        private final String dishName;
        private boolean delivered;
        public FoodOrder(String studentName, String dishName) {
            if (studentName == null || studentName.trim().isEmpty()) {
                throw new IllegalArgumentException("studentName is missing or blank.");
            }
            if (dishName == null || dishName.trim().isEmpty()) {
                throw new IllegalArgumentException("dishName is missing or blank.");
            }
            this.studentName = studentName;
            this.dishName = dishName;
            this.delivered = false;
        }
        void markDelivered() {
            if (delivered) {
                System.out.println("WARNING: " + studentName + "'s " + dishName
                        + " was already marked delivered! Possible double-serve.");
            } else {
                delivered = true;
                System.out.println(studentName + "'s " + dishName + " marked as delivered.");
            }
        }
    }
    static void processBatch(String[][] rawOrders) {
        int valid = 0;
        int rejected = 0;
        for (String[] raw : rawOrders) {
            String studentName = raw.length > 0 ? raw[0] : null;
            String dishName = raw.length > 1 ? raw[1] : null;
            try {
                new FoodOrder(studentName, dishName);
                valid++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }
    public static void main(String[] args) {
        String[][] rawOrders = {
                {"Ravi", "Paneer Butter Masala"},
                {"", "Chole Bhature"},
                {"Meera", " "},
                {"Divya", "Veg Biryani"}
        };
        processBatch(rawOrders); // Expected: Valid: 2 | Rejected: 2
        System.out.println();
        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered(); // normal delivery message
        order.markDelivered(); // duplicate-delivery warning
    }
}