class CampusPin {
    public static String banner = "Campus";
    public String name = "General Pin";
    public String getDetails() { return "Details: " + name; }
}

class HostelPin extends CampusPin {
    public static String banner = "Hostel";       // hides, does not override
    public String name = "Hostel Pin";            // hides, does not override
    @Override
    public String getDetails() { return "Details: " + name; }  // name = HostelPin's field
}

public class expp10_4 {
    public static void main(String[] args) {
        CampusPin p = new HostelPin();

        System.out.println("banner: " + p.banner.replace("", "") + " (decided by reference type)");
        System.out.println("name: " + p.name + " (decided by reference type)");
        System.out.println(p.getDetails() + " (decided by object type)");
    }
}
