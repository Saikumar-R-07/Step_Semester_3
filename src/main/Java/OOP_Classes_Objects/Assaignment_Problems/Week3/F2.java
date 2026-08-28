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

    public String getEmpName() {
        return empName;
    }
}

// Employee.java is never reopened -- new behaviour is added purely by extending it.
class ManagerEmployee extends Employee {
    private double teamBonus;

    public ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    public double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    public InternEmployee(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    public double effectiveSalary() {
        return Math.min(getSalary(), stipendCap);
    }
}

public class F2 {
    public static void main(String[] args) {
        Employee plain = new Employee(1, "Plain", 40000);
        Employee manager = new ManagerEmployee(2, "Manager", 70000, 8000);
        Employee intern = new InternEmployee(3, "Intern", 12000, 10000);

        Employee[] employees = { plain, manager, intern };

        for (Employee e : employees) {
            if (e instanceof ManagerEmployee) {
                System.out.println("Manager effective pay: Rs " + ((ManagerEmployee) e).effectiveSalary());
            } else if (e instanceof InternEmployee) {
                System.out.println("Intern effective pay: Rs " + ((InternEmployee) e).effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " + e.getSalary());
            }
        }
    }
}
