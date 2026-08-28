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
    public String getRegNo() {
        return regNo;
    }
}
// Extends FeeAccount without touching FeeAccount.java itself.
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
// Second, independent extension point on the same tested base class.
class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent;
    public ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
        super(regNo, totalFee);
        this.scholarshipPercent = scholarshipPercent;
    }
    public double effectiveDue() {
        return getDue() * (1 - scholarshipPercent / 100.0);
    }
}
public class F2_FeeAccountInheritance {
    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA101", 150000);
        plain.pay(150000);
        HostelFeeAccount hostel = new HostelFeeAccount("RA102", 200000);
        hostel.payInTwoInstallments(60000);
        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA103", 180000, 20);
        FeeAccount[] accounts = { plain, hostel, scholarship };
        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount sa = (ScholarshipFeeAccount) acc;
                System.out.println("Scholarship account effective due: Rs " + sa.effectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + acc.getDue());
            } else {
                System.out.println("Plain account due: Rs " + acc.getDue());
            }
        }
    }
}