class HostelRoom {
    private String roomNo;
    private int beds;
    private int occupied;
    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }
    public void allot(String name) {
        if (occupied < beds) {
            occupied++;
        }
    }
    public boolean hasSpace() {
        return occupied < beds;
    }
    public String getRoomNo() {
        return roomNo;
    }
}
public class F3_HostelRoomAllocation {
    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        for (HostelRoom room : rooms) {
            if (room.hasSpace()) {
                return room;
            }
        }
        return null;
    }
    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom room = findAvailableRoom(rooms);
        if (room == null) {
            System.out.println("No rooms available for " + studentName);
            return;
        }
        room.allot(studentName);
        System.out.println(studentName + " allotted to room " + room.getRoomNo());
    }
    public static void main(String[] args) {
        // Case 1: a room with space exists.
        HostelRoom[] rooms1 = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(rooms1, "Divya");
        // Case 2: every room is already full.
        HostelRoom[] rooms2 = {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(rooms2, "Divya");
    }
}