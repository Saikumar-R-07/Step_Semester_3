class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;
    public FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = 0.0;
    }
    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Rejected: payment must be positive.");
            return;
        }
        amountPaid += amount;
    }
    public double getDue() {
        return totalFee - amountPaid;
    }
}
class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }
    public void payInTwoInstallments(double amount) {
        double half = amount / 2;
        pay(half);
        pay(amount - half);
    }
}
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
// Ties a student's identity together with two OTHER objects (a
// HostelFeeAccount and a HostelRoom) -- demonstrating composition:
// an object's fields can themselves be objects.
class SrmStudent {
    private String name;
    private String regNo;
    private HostelFeeAccount feeAccount;
    private HostelRoom room; // may be null if unallotted
    private static int totalStudents = 0;
    public SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;
        totalStudents++;
    }
    public void assignRoom(HostelRoom room) {
        this.room = room;
    }
    public String fullStatus() {
        String roomInfo = (room == null) ? "unallotted" : room.getRoomNo();
        return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomInfo;
    }
    public static int getTotalStudents() {
        return totalStudents;
    }
}
public class F5_CapstoneSystem {
    public static void main(String[] args) {
        HostelFeeAccount raviFee = new HostelFeeAccount("RA201", 200000);
        HostelFeeAccount anithaFee = new HostelFeeAccount("RA202", 200000);
        HostelFeeAccount karthikFee = new HostelFeeAccount("RA203", 200000);
        SrmStudent ravi = new SrmStudent("Ravi", "RA201", raviFee);
        SrmStudent anitha = new SrmStudent("Anitha", "RA202", anithaFee);
        SrmStudent karthik = new SrmStudent("Karthik", "RA203", karthikFee);
        HostelRoom roomC214 = new HostelRoom("C-214", 2, 0);
        HostelRoom roomC507 = new HostelRoom("C-507", 2, 0);
        // Only two of the three students get a room; Karthik stays unallotted.
        roomC214.allot("Ravi");
        ravi.assignRoom(roomC214);
        roomC507.allot("Anitha");
        anitha.assignRoom(roomC507);
        // Mix of valid and rejected payments.
        raviFee.payInTwoInstallments(60000);
        anithaFee.pay(20000);
        karthikFee.pay(-500); // rejected: non-positive amount
        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());
        System.out.println("Total students: " + SrmStudent.getTotalStudents());
    }
}