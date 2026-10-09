import java.util.Arrays;

class EventTicket {
protected double basePrice;
protected double amountPaid;

```
private double[] lateFeeHistory = new double[10];
private int feeCount = 0;

public EventTicket(double basePrice) {
    if (basePrice <= 0) {
        throw new IllegalArgumentException("Price must be positive");
    }
    this.basePrice = basePrice;
}

public void pay(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Payment must be positive");
    }
    amountPaid += amount;
}

public double getBalanceDue() {
    return Math.max(0, basePrice - amountPaid);
}

protected void applyLateFee(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Fee must be positive");
    }

    if (feeCount >= lateFeeHistory.length) {
        throw new IllegalStateException("Late fee limit reached");
    }

    basePrice += amount;
    lateFeeHistory[feeCount++] = amount;
}

public double[] getLateFeeHistory() {
    return Arrays.copyOf(lateFeeHistory, feeCount);
}
```

}

class WorkshopTicket extends EventTicket {

```
public WorkshopTicket(double basePrice) {
    super(basePrice);
}

@Override
protected void applyLateFee(double amount) {
    super.applyLateFee(amount * 2);
}
```

}

public class Problem3 {
public static void main(String[] args) {
WorkshopTicket w = new WorkshopTicket(1200);

```
    w.pay(1200);

    w.applyLateFee(100);

    System.out.println(w.getBalanceDue());

    double[] history = w.getLateFeeHistory();
    System.out.println(Arrays.toString(history));

    history[0] = 999;

    System.out.println(
        Arrays.toString(w.getLateFeeHistory())
    );
}
```

}
