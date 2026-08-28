// ---------- Broken version ----------
class LibraryMemberBroken {
    // WRONG: name, memberId, and booksIssued describe ONE specific member.
    // Marking them static makes every member share a single copy of each
    // field, so creating a second member silently overwrites the first
    // member's name/id/booksIssued -- there is no per-object identity left.
    static String name;
    static String memberId;
    static int booksIssued;

    public LibraryMemberBroken(String name, String memberId, int booksIssued) {
        LibraryMemberBroken.name = name;
        LibraryMemberBroken.memberId = memberId;
        LibraryMemberBroken.booksIssued = booksIssued;
    }
}

// ---------- Fixed version ----------
class LibraryMember {
    // Instance fields: each member needs their OWN independent copy.
    private String name;
    private String memberId;
    private int booksIssued;

    // Static fields: shared, library-wide data that isn't tied to one member.
    private static String libraryName = "City Central Library";
    private static int memberCount = 0;

    public LibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + (1000 + memberCount);
    }

    public void printMemberCard() {
        System.out.println(name + " | " + memberId);
    }

    public static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

public class F4 {
    public static void main(String[] args) {
        System.out.println("Broken version:");
        LibraryMemberBroken m1 = new LibraryMemberBroken("Aditi", "X", 1);
        LibraryMemberBroken m2 = new LibraryMemberBroken("Rohan", "Y", 2);
        System.out.println(LibraryMemberBroken.name);
        System.out.println(LibraryMemberBroken.name);
        System.out.println("(Aditi's data was overwritten -- both members now show \"Rohan\")");

        System.out.println();
        System.out.println("Fixed version:");
        LibraryMember a = new LibraryMember("Aditi", 1);
        LibraryMember r = new LibraryMember("Rohan", 2);
        a.printMemberCard();
        r.printMemberCard();
        LibraryMember.printTotalMembers();
    }
}
