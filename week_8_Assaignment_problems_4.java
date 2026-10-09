import java.util.*;

// Question 4: The Elective Seat Rush
public class week_8_Assaignment_problems_4 {
    interface CreditPolicy {
        int limit();
        String type();
    }
    static class RegularPolicy implements CreditPolicy {
        public int limit() { return 24; }
        public String type() { return "Regular"; }
    }
    static class HonorsPolicy implements CreditPolicy {
        public int limit() { return 28; }
        public String type() { return "Honors"; }
    }
    static class ExchangePolicy implements CreditPolicy {
        public int limit() { return 20; }
        public String type() { return "Exchange"; }
    }

    static class Student {
        final String name;
        final CreditPolicy policy;
        private int currentCredits;
        final Set<String> enrolled = new HashSet<>();
        final Set<String> waiting = new HashSet<>();
        Student(String name, CreditPolicy policy, int currentCredits) {
            this.name = name; this.policy = policy; this.currentCredits = currentCredits;
        }
        int credits() { return currentCredits; }
        boolean canAdd(int credits) { return currentCredits + credits <= policy.limit(); }
        void addCredits(int credits) { currentCredits += credits; }
        void removeCredits(int credits) { currentCredits -= credits; }
    }

    static class Elective {
        final String name;
        final int credits, capacity;
        private final List<Student> enrolled = new ArrayList<>();
        private final Queue<Student> waitlist = new ArrayDeque<>();
        Elective(String name, int credits, int capacity) {
            if (credits <= 0 || capacity <= 0) throw new IllegalArgumentException("Credits and capacity must be positive.");
            this.name = name; this.credits = credits; this.capacity = capacity;
        }
        boolean hasSeat() { return enrolled.size() < capacity; }
        boolean contains(Student s) { return enrolled.contains(s); }
        boolean isWaiting(Student s) { return waitlist.contains(s); }
        boolean enrollDirect(Student s) {
            if (!hasSeat()) return false;
            enrolled.add(s);
            s.enrolled.add(name);
            s.addCredits(credits);
            System.out.println(s.name + " enrolled in " + name + " (credits: "
                    + s.credits() + "/" + s.policy.limit() + ").");
            return true;
        }
        void addToWaitlist(Student s) {
            waitlist.offer(s);
            s.waiting.add(name);
            System.out.println(s.name + " added to waitlist (position " + waitlist.size() + ").");
        }
        void drop(Student s) {
            if (!enrolled.remove(s)) {
                System.out.println("Drop failed: " + s.name + " is not enrolled in " + name + ".");
                return;
            }
            s.enrolled.remove(name);
            s.removeCredits(credits);
            System.out.println(s.name + " dropped " + name + " (credits: "
                    + s.credits() + "/" + s.policy.limit() + ").");
            promoteNextEligible();
        }
        private void promoteNextEligible() {
            while (!waitlist.isEmpty() && hasSeat()) {
                Student candidate = waitlist.poll();
                candidate.waiting.remove(name);
                if (candidate.enrolled.contains(name)) continue;
                if (!candidate.canAdd(credits)) {
                    System.out.println("Promotion skipped: " + candidate.name
                            + " would exceed the " + candidate.policy.type() + " credit limit.");
                    continue;
                }
                enrollDirect(candidate);
                System.out.println(candidate.name + " promoted from waitlist and enrolled in " + name
                        + " (credits: " + candidate.credits() + "/" + candidate.policy.limit() + ").");
                break;
            }
        }
    }

    static class EnrollmentService {
        void enroll(Student s, Elective e) {
            if (s.enrolled.contains(e.name) || s.waiting.contains(e.name)
                    || e.contains(s) || e.isWaiting(s)) {
                System.out.println("Enrollment failed: " + s.name
                        + " is already enrolled or waitlisted for " + e.name + ".");
                return;
            }
            // Credit limit must be checked before checking seat availability.
            if (!s.canAdd(e.credits)) {
                System.out.println("Enrollment failed: " + s.name + " would exceed the "
                        + s.policy.type() + " credit limit (" + (s.credits() + e.credits)
                        + "/" + s.policy.limit() + ").");
                return;
            }
            if (e.hasSeat()) e.enrollDirect(s);
            else {
                System.out.println(e.name + " is full.");
                e.addToWaitlist(s);
            }
        }
        void drop(Student s, Elective e) { e.drop(s); }
    }

    public static void main(String[] args) {
        Elective cloud = new Elective("Cloud Computing", 4, 2);
        EnrollmentService service = new EnrollmentService();
        Student asha = new Student("Asha", new RegularPolicy(), 20);
        Student ravi = new Student("Ravi", new HonorsPolicy(), 22);
        Student neha = new Student("Neha", new ExchangePolicy(), 12);
        Student kiran = new Student("Kiran", new RegularPolicy(), 22);

        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);
        service.enroll(kiran, cloud);
        service.drop(asha, cloud);
    }
}
