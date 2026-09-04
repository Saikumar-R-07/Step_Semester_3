public class wk_4as3 {
    static class Canteen {
        private String canteenCode;
        private String canteenName;
        private int trustScore;
        public Canteen(String canteenCode, String canteenName, int trustScore) {
            this.canteenCode = canteenCode;
            this.canteenName = canteenName;
            this.trustScore = trustScore;
        }
        public Canteen(String canteenCode, String canteenName) {
            this(canteenCode, canteenName, 3);
        }
        int compareTo(Canteen other) {
            if (this.trustScore != other.trustScore) {
                return other.trustScore - this.trustScore; // higher score ranks first
            }
            int codeCompare = this.canteenCode.compareToIgnoreCase(other.canteenCode);
            if (codeCompare != 0) {
                return codeCompare; // case-insensitive code order
            }
            return this.canteenName.length() - other.canteenName.length(); // shorter name first
        }
        public String getCanteenCode() {
            return canteenCode;
        }
        public String getCanteenName() {
            return canteenName;
        }
        public int getTrustScore() {
            return trustScore;
        }
    }
    static Canteen[] rankCanteens(Canteen[] canteens) {
        Canteen[] ranked = canteens.clone();
        for (int i = 0; i < ranked.length - 1; i++) {
            int bestIdx = i;
            for (int j = i + 1; j < ranked.length; j++) {
                if (ranked[j].compareTo(ranked[bestIdx]) < 0) {
                    bestIdx = j;
                }
            }
            if (bestIdx != i) {
                Canteen temp = ranked[i];
                ranked[i] = ranked[bestIdx];
                ranked[bestIdx] = temp;
            }
        }
        return ranked;
    }
    public static void main(String[] args) {
        Canteen[] canteens = {
                new Canteen("HB3-C", "Spice Junction", 3),
                new Canteen("hb1-c", "Grand Mess", 5),
                new Canteen("HB2-C", "Southern Treats") // defaults to trustScore 3
        };
        Canteen[] ranked = rankCanteens(canteens);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < ranked.length; i++) {
            sb.append("\"").append(ranked[i].getCanteenCode()).append("\"");
            if (i < ranked.length - 1) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb); // ["hb1-c", "HB2-C", "HB3-C"]
    }
}