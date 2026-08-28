class SrmStudent {
    private String name;
    private String regNo;
    private int attendance;
    public SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }
    public void addAttendanceUpdate(int newAttendance) {
        this.attendance = newAttendance;
    }
    public boolean isEligible() {
        return attendance >= 75;
    }
    public String getName() {
        return name;
    }
    public int getAttendance() {
        return attendance;
    }
    public static double classAverage(SrmStudent[] students) {
        int total = 0;
        for (SrmStudent s : students) {
            total += s.getAttendance();
        }
        return (double) total / students.length;
    }
}
public class F1_AttendanceSystem {
    public static void main(String[] args) {
        SrmStudent[] students = new SrmStudent[5];
        students[0] = new SrmStudent("Ravi", "RA001", 82);
        students[1] = new SrmStudent("Anitha", "RA002", 68);
        students[2] = new SrmStudent("Karthik", "RA003", 91);
        students[3] = new SrmStudent("Meera", "RA004", 74);
        students[4] = new SrmStudent("Suresh", "RA005", 60);
        for (SrmStudent s : students) {
            String status = s.isEligible() ? "Eligible" : "Detained";
            System.out.println(s.getName() + " - " + s.getAttendance() + "% - " + status);
        }
        System.out.println("Class average: " + SrmStudent.classAverage(students) + "%");
    }
}