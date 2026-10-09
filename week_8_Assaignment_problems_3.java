import java.util.*;

// Question 3: The Smart Lab Control Panel
public class week_8_Assaignment_problems_3 {
    interface Capability {
        String name();
        boolean apply(String value, String deviceName);
    }

    static class PowerCapability implements Capability {
        private boolean on;
        public String name() { return "Power"; }
        public boolean apply(String value, String device) {
            if (!value.equalsIgnoreCase("ON") && !value.equalsIgnoreCase("OFF")) {
                System.out.println("Rejected: " + device + " power must be ON or OFF.");
                return false;
            }
            on = value.equalsIgnoreCase("ON");
            System.out.println(device + ": " + (on ? "ON" : "OFF") + ".");
            return true;
        }
    }

    static class BrightnessCapability implements Capability {
        private int brightness;
        public String name() { return "Brightness"; }
        public boolean apply(String value, String device) {
            try {
                int v = Integer.parseInt(value);
                if (v < 0 || v > 100) throw new NumberFormatException();
                brightness = v;
                System.out.println(device + ": brightness set to " + brightness + "%.");
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Rejected: " + device + " brightness must be between 0% and 100%.");
                return false;
            }
        }
    }

    static class TemperatureCapability implements Capability {
        private int temperature;
        public String name() { return "Temperature"; }
        public boolean apply(String value, String device) {
            try {
                int v = Integer.parseInt(value);
                if (v < 16 || v > 30) throw new NumberFormatException();
                temperature = v;
                System.out.println(device + ": temperature set to " + temperature + "°C.");
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Rejected: " + device + " temperature must be between 16°C and 30°C.");
                return false;
            }
        }
    }

    static class Device {
        final String name;
        private final Map<String, Capability> capabilities = new LinkedHashMap<>();
        Device(String name, Capability... caps) {
            this.name = name;
            for (Capability c : caps) addCapability(c);
        }
        void addCapability(Capability capability) {
            capabilities.put(capability.name().toLowerCase(Locale.ROOT), capability);
            System.out.println(name + ": " + capability.name() + " capability added.");
        }
        boolean supports(String capability) {
            return capabilities.containsKey(capability.toLowerCase(Locale.ROOT));
        }
        boolean apply(String capability, String value) {
            Capability c = capabilities.get(capability.toLowerCase(Locale.ROOT));
            return c != null && c.apply(value, name);
        }
    }

    static class SceneStep {
        final String capability, value;
        SceneStep(String capability, String value) {
            this.capability = capability; this.value = value;
        }
    }

    static class Scene {
        final String name;
        final List<SceneStep> steps = new ArrayList<>();
        Scene(String name) { this.name = name; }
        Scene addStep(String capability, String value) {
            steps.add(new SceneStep(capability, value));
            return this;
        }
        void execute(List<Device> devices) {
            System.out.println("Scene '" + name + "' started.");
            int applied = 0;
            for (SceneStep step : steps) {
                for (Device device : devices) {
                    if (device.supports(step.capability) &&
                        device.apply(step.capability, step.value)) applied++;
                }
            }
            System.out.println("Scene '" + name + "' completed: " + applied + " actions applied.");
        }
    }

    public static void main(String[] args) {
        Device ac = new Device("Lab AC", new PowerCapability(), new TemperatureCapability());
        Device lights = new Device("Ceiling Lights", new PowerCapability(), new BrightnessCapability());
        Device projector = new Device("Projector", new PowerCapability());
        List<Device> devices = Arrays.asList(ac, lights, projector);

        Scene lecture = new Scene("Lecture Mode")
                .addStep("Power", "ON")
                .addStep("Brightness", "40")
                .addStep("Temperature", "24");
        lecture.execute(devices);

        ac.apply("Temperature", "12");
        projector.addCapability(new BrightnessCapability());
        projector.apply("Brightness", "70");
    }
}
