import java.util.LinkedHashMap;
import java.util.Map;

public class Problem1 {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier.equals("public")) return "ALLOWED";
        if (fieldModifier.equals("private")) return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";
        if (fieldModifier.equals("default")) return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")) ? "ALLOWED" : "DENIED";
        if (fieldModifier.equals("protected")) return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE")) ? "ALLOWED" : "DENIED";
        return "DENIED";
    }

    public static String summarizeByModifier(String[][] attempts) {
        Map<String, int[]> counts = new LinkedHashMap<>();
        counts.put("private", new int[]{0, 0});
        counts.put("default", new int[]{0, 0});
        counts.put("protected", new int[]{0, 0});
        counts.put("public", new int[]{0, 0});

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt == null || attempt.length < 2) continue;
                String res = classifyAccess(attempt[0], attempt[1]);
                if (counts.containsKey(attempt[0])) {
                    if ("ALLOWED".equals(res)) counts.get(attempt[0])[0]++;
                    else counts.get(attempt[0])[1]++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, int[]> entry : counts.entrySet()) {
            if (!first) sb.append(" | ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue()[0]).append(" allowed / ").append(entry.getValue()[1]).append(" denied");
            first = false;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS"));
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE"));
        
        String[][] attempts = {
            {"private", "SAME_CLASS"}, {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"}, {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"}, {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(attempts));

        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }
    }
}

class LibraryMember {
    private String membershipId;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
        if (membershipId == null || membershipId.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.membershipId = membershipId.trim();
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }
}
