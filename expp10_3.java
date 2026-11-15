public class expp10_3 {
    public static void main(String[] args) {
        String[] subjects = {"Math", "Sci", "Eng"};
        String[] students = {"Anu", "Ravi", "Meena"};

        // Jagged array: missing marks are simply absent
        int[][] marks = {
            {80, 90, 70},
            {60, 85},
            {95, 75, 88}
        };

        // Totals
        StringBuilder totals = new StringBuilder("Totals ");
        for (int i = 0; i < students.length; i++) {
            int sum = 0;
            for (int m : marks[i]) sum += m;
            totals.append(students[i]).append(" ").append(sum);
            if (i < students.length - 1) totals.append(", ");
        }
        System.out.println(totals);

        // Toppers per subject
        StringBuilder toppers = new StringBuilder("toppers ");
        for (int s = 0; s < subjects.length; s++) {
            int best = -1;
            String topper = "";
            for (int i = 0; i < students.length; i++) {
                if (s < marks[i].length && marks[i][s] > best) {  // skip missing
                    best = marks[i][s];
                    topper = students[i];
                }
            }
            toppers.append(subjects[s]).append(" ").append(topper)
                   .append(" ").append(best);
            if (s < subjects.length - 1) toppers.append(", ");
        }
        System.out.println(toppers);
    }
}
