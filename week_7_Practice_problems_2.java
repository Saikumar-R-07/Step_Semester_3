public class week_7_Practice_problems_2 {
    interface Alertable {
        String sendAlert(String message);
    }

    static class SecuritySensor {
        protected String zoneName;

        public SecuritySensor(String zoneName) {
            if (zoneName == null || zoneName.trim().isEmpty())
                throw new IllegalArgumentException("Zone name cannot be blank.");
            this.zoneName = zoneName;
        }

        public String getZoneName() {
            return zoneName;
        }
    }

    static class MotionSensor extends SecuritySensor implements Alertable {
        public MotionSensor(String zoneName) {
            super(zoneName);
        }

        @Override
        public String sendAlert(String message) {
            return "[" + zoneName + "] " + message;
        }
    }

    static class DualZoneMotionSensor extends MotionSensor {
        private final String secondZoneName;

        public DualZoneMotionSensor(String zoneName, String secondZoneName) {
            super(zoneName);
            if (secondZoneName == null || secondZoneName.trim().isEmpty())
                throw new IllegalArgumentException("Second zone cannot be blank.");
            this.secondZoneName = secondZoneName;
        }

        @Override
        public String sendAlert(String message) {
            return super.sendAlert(message) + " [also covering " + secondZoneName + "]";
        }
    }

    static class SmokeDetector implements Alertable {
        private final String deviceId;

        public SmokeDetector(String deviceId) {
            if (deviceId == null || deviceId.trim().isEmpty())
                throw new IllegalArgumentException("Device ID cannot be blank.");
            this.deviceId = deviceId;
        }

        @Override
        public String sendAlert(String message) {
            return "[" + deviceId + "] " + message;
        }
    }

    public static void broadcastAll(Alertable[] devices, String message) {
        for (Alertable device : devices) {
            System.out.println(device.sendAlert(message));
        }
    }

    public static String getZoneIfMotionSensor(Alertable a) {
        if (a instanceof MotionSensor) {
            MotionSensor sensor = (MotionSensor) a;
            return sensor.getZoneName();
        }
        return "Not a motion sensor";
    }

    public static void main(String[] args) {
        MotionSensor motion = new MotionSensor("Living Room");
        DualZoneMotionSensor dual = new DualZoneMotionSensor("Hallway", "Stairwell");
        SmokeDetector smoke = new SmokeDetector("SD-01");

        System.out.println(motion.sendAlert("Motion detected"));
        System.out.println(dual.sendAlert("Motion detected"));
        System.out.println(smoke.sendAlert("Smoke detected"));
        System.out.println(getZoneIfMotionSensor(motion));
        System.out.println(getZoneIfMotionSensor(smoke));

        Alertable[] devices = {motion, dual, smoke};
        broadcastAll(devices, "Safety alert");
    }
}
