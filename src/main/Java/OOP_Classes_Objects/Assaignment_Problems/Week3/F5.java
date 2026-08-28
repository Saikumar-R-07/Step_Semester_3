class Employee {
    private int empId;
    private String empName;
    private double salary;

    public Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public double effectiveSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    @Override
    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

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
}

// An object's fields can themselves be objects: here `employee` and `slot`
// are references to other objects, not primitive values -- that's composition.
class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    public CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    public String fullProfile() {
        String slotInfo = (slot == null) ? "no parking assigned" : slot.getSlotNo();
        return name + " | Pay: Rs " + employee.effectiveSalary() + " | Slot: " + slotInfo;
    }
}

public class F5 {
    public static void main(String[] args) {
        ParkingSlot a1 = new ParkingSlot("A1", 1, 0);
        ParkingSlot a2 = new ParkingSlot("A2", 1, 0);

        Employee divyaEmp = new ManagerEmployee(1, "Divya", 70000, 8000);
        Employee karanEmp = new Employee(2, "Karan", 40000);
        Employee meeraEmp = new ManagerEmployee(3, "Meera", 8000, 2000); // effective capped example

        CompanyEmployeeRecord divya = new CompanyEmployeeRecord("Divya", "E1", divyaEmp, null);
        CompanyEmployeeRecord karan = new CompanyEmployeeRecord("Karan", "E2", karanEmp, null);
        CompanyEmployeeRecord meera = new CompanyEmployeeRecord("Meera", "E3", meeraEmp, null);

        // Allot parking to only two of the three records, on purpose.
        ParkingSlot slotForDivya = ParkingSlot.findAvailableSlot(new ParkingSlot[]{a1, a2});
        if (slotForDivya != null) {
            slotForDivya.allot("DIVYA-CAR");
            divya.slot = slotForDivya;
        }
        ParkingSlot slotForKaran = ParkingSlot.findAvailableSlot(new ParkingSlot[]{a1, a2});
        if (slotForKaran != null) {
            slotForKaran.allot("KARAN-CAR");
            karan.slot = slotForKaran;
        }
        // Meera's slot is deliberately left null (unallotted).

        System.out.println(divya.fullProfile());
        System.out.println(karan.fullProfile());
        System.out.println(meera.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
