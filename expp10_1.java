import java.util.*;

interface Dimmable { String dim(int level); }
interface Schedulable { String schedule(String time); }
interface EnergyMonitor { String energy(); }
abstract class expp10_1 {
    protected final String id;
    protected boolean on;
    Device(String id) { this.id = id; }
    String turnOn()  { on = true;  return id + " is ON"; }
    String turnOff() { on = false; return id + " is OFF"; }
    String getId()   { return id; }
}

class Light extends Device implements Dimmable, Schedulable {
    Light(String id) { super(id); }
    public String dim(int level) { return id + " dimmed to " + level; }
    public String schedule(String t) { return id + " scheduled " + t; }
}

class Fan extends Device implements Schedulable {
    Fan(String id) { super(id); }
    public String schedule(String t) { return id + " scheduled " + t; }
}

class Plug extends Device implements EnergyMonitor {
    private final int kwh;
    Plug(String id, int kwh) { super(id); this.kwh = kwh; }
    public String energy() { return id + " energy " + kwh + " kWh"; }
}

public class SmartHome {
    static Map<String, Device> devices = new LinkedHashMap<>();

    static String process(String cmd) {
        String[] p = cmd.split(" ");
        Device d = devices.get(p[1]);
        if (d == null) return p[1] + " not found";

        switch (p[0]) {
            case "ON":  return d.turnOn();
            case "OFF": return d.turnOff();
            case "DIM":
                return (d instanceof Dimmable)
                    ? ((Dimmable) d).dim(Integer.parseInt(p[2]))
                    : d.getId() + " rejected: DIM unsupported";
            case "SCHEDULE":
                return (d instanceof Schedulable)
                    ? ((Schedulable) d).schedule(p[2])
                    : d.getId() + " rejected: SCHEDULE unsupported";
            case "ENERGY":
                return (d instanceof EnergyMonitor)
                    ? ((EnergyMonitor) d).energy()
                    : d.getId() + " rejected: ENERGY unsupported";
            default: return "Unknown command";
        }
    }

    public static void main(String[] args) {
        devices.put("L1", new Light("L1"));
        devices.put("F1", new Fan("F1"));
        devices.put("P1", new Plug("P1", 12));

        String[] commands = {
            "ON L1", "DIM L1 40", "DIM F1 30",
            "SCHEDULE F1 22:00", "ENERGY P1", "ENERGY L1"
        };
        for (String c : commands) System.out.println(process(c));
    }
}
