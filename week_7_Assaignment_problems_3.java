public class week_7_Assaignment_problems_3 {

    interface Insurable {
        String getInsuranceInfo();
    }

    static abstract class ServiceableVehicle {
        private double mileage;

        public abstract String performMaintenance();

        public double getMileage() {
            return mileage;
        }

        public void addMileage(double km) {
            if (km < 0) {
                throw new IllegalArgumentException("Distance cannot be negative.");
            }
            mileage += km;
        }
    }

    static class Forklift extends ServiceableVehicle implements Insurable {
        protected final String assetTag;

        public Forklift(String assetTag) {
            if (assetTag == null || assetTag.trim().isEmpty()) {
                throw new IllegalArgumentException("Asset tag cannot be blank.");
            }
            this.assetTag = assetTag;
        }

        @Override
        public String performMaintenance() {
            return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
        }

        @Override
        public String getInsuranceInfo() {
            return "Insured under fleet policy - Asset " + assetTag;
        }
    }

    static class HeavyDutyForklift extends Forklift {
        public HeavyDutyForklift(String assetTag) {
            super(assetTag);
        }

        @Override
        public String performMaintenance() {
            return super.performMaintenance()
                    + " | high-pressure hydraulic check complete";
        }
    }

    static String getInsuranceIfApplicable(ServiceableVehicle vehicle) {
        if (vehicle instanceof Insurable) {
            return ((Insurable) vehicle).getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        Forklift f = new Forklift("FL-22");
        f.addMileage(120);
        System.out.println("Mileage: " + f.getMileage());
        System.out.println(f.performMaintenance());
        System.out.println(getInsuranceIfApplicable(f));

        HeavyDutyForklift hd = new HeavyDutyForklift("HD-9");
        System.out.println(hd.performMaintenance());
        hd.addMileage(250.5);
        System.out.println("Heavy-duty forklift mileage: " + hd.getMileage());
        System.out.println(getInsuranceIfApplicable(hd));
    }
}
