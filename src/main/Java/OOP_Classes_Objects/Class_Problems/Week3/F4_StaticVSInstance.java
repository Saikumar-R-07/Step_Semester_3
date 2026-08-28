// ---------- BROKEN VERSION ----------
// Every field is static, so there is only ONE copy of name/regNo/attendance
// shared by ALL SrmStudent objects. Creating a second student overwrites
// the first student's data, because they are not independent objects at
// all -- they are just two handles pointing at the same class-level slots.
class BrokenSrmStudent {
    static String name;
    static String regNo;
    static int attendance;
    public BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }
}
/*
 * Why static is wrong for each field here:
 * - name: each student needs their OWN name; static makes it one shared
 *   name for the entire class, so the latest constructor call wins.
 * - regNo: same problem -- registration numbers must be unique per
 *   student, but static collapses them into a single shared value.
 * - attendance: attendance is inherently per-student data; sharing it
 *   means updating one student's attendance silently changes it for
 *   every other student too.
 */

// ---------- FIXED VERSION ----------
// name/regNo/attendance are instance fields (one copy per object).
// university/admissionCount are genuinely class-wide facts, so they
// correctly remain static.
class SrmStudent {
    private String name;
    private String regNo;
    private int attendance;
    private static String university = "SRM Institute of Science and Technology";
    private static int admissionCount = 0;
    public SrmStudent(String name, int attendance) {
        this.name = name;
        admissionCount++;
        this.regNo = "RA23110030101" + admissionCount;
        this.attendance = attendance;
    }
    public void printIdCard() {
        System.out.println(name + " | " + regNo);
    }
    public static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}
public class F4_StaticVSInstance {
    public static void main(String[] args) {
        System.out.println("--- Broken version ---");
        BrokenSrmStudent b1 = new BrokenSrmStudent("Ravi", "RA231100301011", 82);
        BrokenSrmStudent b2 = new BrokenSrmStudent("Meera", "RA231100301012", 74);
        // Both "objects" read from the same static fields, so both print Meera.
        System.out.println(BrokenSrmStudent.name);
        System.out.println(BrokenSrmStudent.name);
        System.out.println("(Ravi's data was overwritten - both students now show \"Meera\")");
        System.out.println("\n--- Fixed version ---");
        SrmStudent s1 = new SrmStudent("Ravi", 82);
        SrmStudent s2 = new SrmStudent("Meera", 74);
        s1.printIdCard();
        s2.printIdCard();
        SrmStudent.printTotalAdmissions();
    }
}