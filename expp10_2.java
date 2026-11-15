import java.util.*;

class Customer {
    private final int id;
    private final String name;

    Customer(int id, String name) { this.id = id; this.name = name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer c = (Customer) o;
        return id == c.id && name.equals(c.name);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name); }

    @Override
    public String toString() { return "(" + id + ", " + name + ")"; }
}

class CustomerRegistry {
    private final Set<Customer> customers = new HashSet<>();

    boolean add(Customer c)      { return customers.add(c); }   // false if duplicate
    boolean contains(Customer c) { return customers.contains(c); }
    int uniqueCount()            { return customers.size(); }
}

public class expp10_2 {
    public static void main(String[] args) {
        CustomerRegistry reg = new CustomerRegistry();

        System.out.println(reg.add(new Customer(101, "Asha")));   // Branch A -> true
        boolean second = reg.add(new Customer(101, "Asha"));      // Branch B
        System.out.println(second + (second ? "" : " (duplicate rejected)"));
        System.out.println(reg.add(new Customer(102, "Ravi")));   // true

        System.out.println("unique count " + reg.uniqueCount());
        System.out.println("contains: " + reg.contains(new Customer(101, "Asha")));
    }
}
