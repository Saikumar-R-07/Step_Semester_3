public class Problem4 {
    public static void main(String[] args) {
        System.out.println(new LibraryMemberJavaBean("Priya Nair").getMembershipId()); // Should be null
        System.out.println(new LibraryMemberJavaBean("LIB-8841", "Priya Nair").getMembershipId());
        LibraryMemberJavaBean m = new LibraryMemberJavaBean();
        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000"); // Should be ignored
        System.out.println(m.getMembershipId()); // Should be "LIB-8841"
    }
}
class LibraryMemberJavaBean {
    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswerHash;
    public LibraryMemberJavaBean() {
        this(null, null);
    }
    public LibraryMemberJavaBean(String name) {
        this(null, name);
    }
    public LibraryMemberJavaBean(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
        this.premiumMember = false;
    }
    public String getMembershipId() { return membershipId; }
    public void setMembershipId(String id) {
        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public boolean isPremiumMember() { return premiumMember; }
    public void setPremiumMember(boolean premium) { this.premiumMember = premium; }
    public void setSecurityAnswer(String answer) {
        if (answer != null) {
            this.securityAnswerHash = String.valueOf(answer.hashCode());
        }
    }
}
