public class week_7_Assaignment_problems_2 {

    interface Exportable {
        String exportData();
    }

    // One shared counter for exports performed by either implementation.
    static class ExportCounter {
        private static int totalExports = 0;

        static void increment() {
            totalExports++;
        }

        static int getTotalExports() {
            return totalExports;
        }
    }

    static class ReportGenerator implements Exportable {
        private final String reportName;

        public ReportGenerator(String reportName) {
            if (reportName == null || reportName.trim().isEmpty()) {
                throw new IllegalArgumentException("Report name cannot be blank.");
            }
            this.reportName = reportName;
        }

        @Override
        public String exportData() {
            ExportCounter.increment();
            return "Exported report: " + reportName;
        }
    }

    static class UserProfile implements Exportable {
        private final String username;

        public UserProfile(String username) {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be blank.");
            }
            this.username = username;
        }

        @Override
        public String exportData() {
            ExportCounter.increment();
            return "Exported profile: " + username;
        }
    }

    static int getTotalExports() {
        return ExportCounter.getTotalExports();
    }

    static void exportAll(Exportable[] items) {
        for (Exportable item : items) {
            if (item != null) {
                System.out.println(item.exportData());
            }
        }
    }

    public static void main(String[] args) {
        ReportGenerator report = new ReportGenerator("Sales Q1");
        UserProfile profile = new UserProfile("jane_doe");

        System.out.println(report.exportData());
        System.out.println(profile.exportData());

        Exportable ref = report;
        exportAll(new Exportable[]{ref, profile});
        System.out.println("Total exports: " + getTotalExports());
    }
}
