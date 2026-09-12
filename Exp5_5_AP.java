import java.util.Arrays;
public class Problem5 {
    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[]{"BK-100", "bad"});
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});
        String[] ids = r.getBookIds();
        ids[0] = "HACKED";
        System.out.println(r.getBookIds()[0]); // Should still print "BK-100"

        // 3. Test nightly circulation processor
        LoanReceipt[] batch = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(NightlyCirculation.processNightlyCirculation(batch));
    }
}
class LoanReceipt {
    private final String memberId;
    private final String[] bookIds;
    public LoanReceipt(String memberId, String[] bookIds) {
        if (bookIds == null) {
            throw new IllegalArgumentException("construction rejected");
        }
        for (String id : bookIds) {
            if (id == null || !id.matches("^BK-\\d{3}$")) {
                throw new IllegalArgumentException("construction rejected");
            }
        }
        this.memberId = memberId;
        // Defensive copy on the way in
        this.bookIds = Arrays.copyOf(bookIds, bookIds.length);
    }
    public String getMemberId() { 
        return memberId; 
    }
    public String[] getBookIds() { 
        // Defensive copy on the way out
        return Arrays.copyOf(bookIds, bookIds.length); 
    }
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        String[] updatedBookIds = getBookIds();
        updatedBookIds[index] = newId;
        return new LoanReceipt(this.memberId, updatedBookIds);
    }
}
class ReferenceOnlyLoanReceipt extends LoanReceipt {
    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }
    
    public String getRoomNumber() { 
        return roomNumber; 
    }
}
class NightlyCirculation {
    // Shared static state block as requested
    static {
    } 
    public static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0, nullSkipped = 0, refOnly = 0, regular = 0;
        if (receipts != null) {
            for (LoanReceipt receipt : receipts) {
                if (receipt == null) {
                    nullSkipped++;
                    continue;
                }  
                processed++;
                // instanceof dispatch to sort subclass from parent class
                if (receipt instanceof ReferenceOnlyLoanReceipt) {
                    refOnly++;
                } else {
                    regular++;
                }
            }
        }
        return String.format("%d processed | %d null skipped | %d reference-only | %d regular", 
                             processed, nullSkipped, refOnly, regular);
    }
}
