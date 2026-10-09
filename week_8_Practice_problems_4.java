import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class week_8_Practice_problems_4 {

    enum Status { PENDING, APPROVED, REJECTED }

    interface LeavePolicy {
        boolean isLeaveAllowed(LocalDate start, LocalDate end);
        String getEmployeeType();
    }

    static class FullTimePolicy implements LeavePolicy {
        public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
            return !start.isAfter(end) && ChronoUnit.DAYS.between(start, end) <= 30;
        }
        public String getEmployeeType() { return "Full-time"; }
    }

    static class PartTimePolicy implements LeavePolicy {
        public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
            return !start.isAfter(end) && ChronoUnit.DAYS.between(start, end) <= 14;
        }
        public String getEmployeeType() { return "Part-time"; }
    }

    static class ContractPolicy implements LeavePolicy {
        public boolean isLeaveAllowed(LocalDate start, LocalDate end) {
            return !start.isAfter(end) && ChronoUnit.DAYS.between(start, end) <= 7;
        }
        public String getEmployeeType() { return "Contract"; }
    }

    static class Employee {
        private final String name;
        private final LeavePolicy policy;

        Employee(String name, LeavePolicy policy) {
            this.name = name;
            this.policy = policy;
        }

        String getName() { return name; }
        LeavePolicy getPolicy() { return policy; }
    }

    static class LeaveRequest {
        private final Employee employee;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private Status status = Status.PENDING;

        LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
            this.employee = employee;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        Status getStatus() { return status; }
        Employee getEmployee() { return employee; }

        boolean review(Status newStatus) {
            if (status != Status.PENDING) {
                System.out.println("Cannot change status: " + status
                        + " request cannot revert to Pending or be reviewed again.");
                return false;
            }
            if (newStatus == Status.PENDING) {
                System.out.println("Cannot change status: request must be Approved or Rejected.");
                return false;
            }
            status = newStatus;
            System.out.println("Leave request for " + employee.getName() + " "
                    + status.toString().toLowerCase() + ". Status: " + status);
            return true;
        }
    }

    static class LeaveManager {
        LeaveRequest submit(Employee employee, LocalDate start, LocalDate end) {
            if (start.isAfter(end)) {
                throw new IllegalArgumentException("Start date must not be after end date.");
            }
            if (!employee.getPolicy().isLeaveAllowed(start, end)) {
                System.out.println("Leave request rejected by "
                        + employee.getPolicy().getEmployeeType() + " leave policy.");
                return null;
            }
            LeaveRequest request = new LeaveRequest(employee, start, end);
            System.out.println("Leave request submitted by " + employee.getName()
                    + " for " + start + " to " + end + ". Status: Pending.");
            return request;
        }

        void review(LeaveRequest request, Status status) {
            if (request != null) request.review(status);
        }
    }

    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();
        Employee john = new Employee("John Doe", new FullTimePolicy());
        Employee jane = new Employee("Jane Smith", new PartTimePolicy());

        LeaveRequest johnRequest = manager.submit(john,
                LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.review(johnRequest, Status.APPROVED);

        manager.submit(jane, LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        manager.review(johnRequest, Status.PENDING);
    }
}
