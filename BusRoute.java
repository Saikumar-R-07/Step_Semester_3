public class BusRoute implements Comparable<BusRoute> {
    private String routeCode;
    private String routeName;
    private int priority;
    public BusRoute(String routeCode, String routeName, int priority) {
        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }
    public BusRoute(String routeCode, String routeName) {
        this(routeCode, routeName, 0);
    }
    public String getRouteCode() {
        return routeCode;
    }
    public int getPriority() {
        return priority;
    }
    public int compareTo(BusRoute other) {
        // Higher priority should come first
        if (this.priority != other.priority) {
            return other.priority - this.priority;
        }
        // Same priority -> compare route codes alphabetically (ignore case)
        return this.routeCode.compareToIgnoreCase(other.routeCode);
    }
    // Simple sort: go through the list, insert each route into the right spot
    public static BusRoute[] rankRoutes(BusRoute[] routes) {
        BusRoute[] result = new BusRoute[routes.length];
        for (int i = 0; i < routes.length; i++) {
            result[i] = routes[i];
        }
        for (int i = 1; i < result.length; i++) {
            BusRoute current = result[i];
            int j = i - 1;
            while (j >= 0 && result[j].compareTo(current) > 0) {
                result[j + 1] = result[j];
                j--;
            }
            result[j + 1] = current;
        }
        return result;
    }
    public static void main(String[] args) {
        BusRoute[] routes = {
            new BusRoute("RT205L", "Airport Express", 3),
            new BusRoute("rt201j", "City Central", 4),
            new BusRoute("RT299T", "Night Service")
        };
        BusRoute[] ranked = rankRoutes(routes);
        for (int i = 0; i < ranked.length; i++) {
            System.out.print(ranked[i].getRouteCode() + " ");
        }
    }
}