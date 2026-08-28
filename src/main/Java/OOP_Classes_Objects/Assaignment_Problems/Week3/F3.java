class ParkingSlot {
    private String slotNo;
    private int capacity;
    private int occupiedCount;

    public ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    public void allot(String vehicleNo) {
        if (occupiedCount < capacity) {
            occupiedCount++;
        }
    }

    public String getSlotNo() {
        return slotNo;
    }

    public boolean hasSpace() {
        return occupiedCount < capacity;
    }

    public static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        for (ParkingSlot slot : slots) {
            if (slot.hasSpace()) {
                return slot;
            }
        }
        return null;
    }

    public static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot slot = findAvailableSlot(slots);
        if (slot == null) {
            System.out.println("No slots available for " + vehicleNo);
        } else {
            slot.allot(vehicleNo);
            System.out.println(vehicleNo + " allotted to slot " + slot.getSlotNo());
        }
    }
}

public class F3 {
    public static void main(String[] args) {
        // Passing the array only copies the array REFERENCE, not the ParkingSlot
        // objects it points to. So calls like slot.allot(...) inside safeAllot
        // mutate the exact same objects the caller holds -- there's no separate
        // "copy" of each ParkingSlot floating around.

        ParkingSlot[] slots1 = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        ParkingSlot.safeAllot(slots1, "TN09AB1234");

        ParkingSlot[] slots2 = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        ParkingSlot.safeAllot(slots2, "TN09AB1234");
    }
}
