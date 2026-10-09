class EventTicket {
protected double basePrice;

```
public EventTicket(double basePrice) {
    this.basePrice = basePrice;
}

public double getBalanceDue() {
    return basePrice;
}

public void printTicket() {
    System.out.print(
        "Standard | Balance: " + getBalanceDue()
    );
}
```

}

class WorkshopTicket extends EventTicket {
private String track;

```
public WorkshopTicket(double basePrice, String track) {
    super(basePrice);
    this.track = track;
}

public String getTrack() {
    return track;
}

@Override
public void printTicket() {
    System.out.print(
        "Workshop | Track: " + track +
        " | Balance: " + getBalanceDue()
    );
}
```

}

public class Problem4 {

```
public static String batchPrint(EventTicket[] tickets) {
    StringBuilder report = new StringBuilder();

    for (EventTicket ticket : tickets) {
        if (report.length() > 0) {
            report.append(" | ");
        }

        if (ticket instanceof WorkshopTicket) {
            report.append("Workshop | Track: ")
                  .append(((WorkshopTicket) ticket).getTrack())
                  .append(" | Balance: ")
                  .append(ticket.getBalanceDue())
                  .append(" [Track via downcast: ")
                  .append(((WorkshopTicket) ticket).getTrack())
                  .append("]");
        } else {
            report.append("Standard | Balance: ")
                  .append(ticket.getBalanceDue());
        }
    }

    return report.toString();
}

public static void main(String[] args) {
    EventTicket[] tickets = {
        new EventTicket(500),
        new WorkshopTicket(1200, "AI/ML")
    };

    System.out.println(batchPrint(tickets));
}
```

}
