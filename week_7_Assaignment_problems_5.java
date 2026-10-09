public class week_7_Assaignment_problems_5 {

    interface RemoteControllable {
        String connect(String appId);
    }

    interface EnergyTrackable {
        double getConsumptionWatts();
    }

    static abstract class HomeDevice {
        private static int counter = 1000;
        private final String serialNumber;

        protected HomeDevice() {
            serialNumber = "HD-" + (++counter);
        }

        public abstract String activate();

        public String getSerialNumber() {
            return serialNumber;
        }
    }

    static class WashingMachine extends HomeDevice
            implements RemoteControllable, EnergyTrackable {
        private final double consumptionWatts;

        public WashingMachine(double consumptionWatts) {
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption must be positive.");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Washing machine " + getSerialNumber() + " started a cycle";
        }

        @Override
        public String connect(String appId) {
            if (appId == null || appId.trim().isEmpty()) {
                throw new IllegalArgumentException("App ID cannot be blank.");
            }
            return getSerialNumber() + " connected to " + appId;
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    static class Refrigerator extends HomeDevice implements EnergyTrackable {
        private final double consumptionWatts;

        public Refrigerator(double consumptionWatts) {
            if (consumptionWatts <= 0) {
                throw new IllegalArgumentException("Consumption must be positive.");
            }
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Refrigerator " + getSerialNumber() + " started cooling";
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    static class MobileApp implements RemoteControllable {
        private final String appName;

        public MobileApp(String appName) {
            if (appName == null || appName.trim().isEmpty()) {
                throw new IllegalArgumentException("App name cannot be blank.");
            }
            this.appName = appName;
        }

        @Override
        public String connect(String appId) {
            if (appId == null || appId.trim().isEmpty()) {
                throw new IllegalArgumentException("App ID cannot be blank.");
            }
            return appName + " connected to " + appId;
        }
    }

    static void connectAll(RemoteControllable[] items, String appId) {
        for (RemoteControllable item : items) {
            if (item != null) {
                System.out.println(item.connect(appId));
            }
        }
    }

    static double getConsumptionIfTrackable(HomeDevice device) {
        if (device instanceof EnergyTrackable) {
            return ((EnergyTrackable) device).getConsumptionWatts();
        }
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine wm = new WashingMachine(500.0);
        Refrigerator fridge = new Refrigerator(150.0);
        MobileApp app = new MobileApp("HomeConnect App");

        System.out.println(wm.activate());
        System.out.println(wm.connect("HomeConnect"));
        System.out.println("Refrigerator consumption: "
                + getConsumptionIfTrackable(fridge));

        HomeDevice ref = wm;
        System.out.println("Washing machine consumption: "
                + getConsumptionIfTrackable(ref));

        System.out.println(app.connect("HomeConnect"));
        connectAll(new RemoteControllable[]{wm, app}, "HomeConnect");
    }
}
