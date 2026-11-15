public class expap11_4 {
    private final int[] data;
    private final int capacity;
    private int front = 0;   // index of the front bus
    private int size = 0;    // number of buses currently parked

    BusBay(int capacity) {
        this.capacity = capacity;
        this.data = new int[capacity];
    }

    boolean insertFront(int bus) {
        if (isFull()) return false;
        front = (front - 1 + capacity) % capacity;   // move front backwards
        data[front] = bus;
        size++;
        return true;
    }

    boolean insertLast(int bus) {
        if (isFull()) return false;
        int rear = (front + size) % capacity;        // first free slot after the rear
        data[rear] = bus;
        size++;
        return true;
    }

    boolean deleteFront() {
        if (isEmpty()) return false;
        front = (front + 1) % capacity;
        size--;
        return true;
    }

    boolean deleteLast() {
        if (isEmpty()) return false;
        size--;                                      // rear shrinks; front is unchanged
        return true;
    }

    int getFront() {
        return isEmpty() ? -1 : data[front];
    }

    int getRear() {
        return isEmpty() ? -1 : data[(front + size - 1) % capacity];
    }

    boolean isEmpty() { return size == 0; }

    boolean isFull()  { return size == capacity; }

    public static void main(String[] args) {
        BusBay b = new BusBay(3);
        System.out.println(b.insertLast(101));   // true
        System.out.println(b.insertLast(102));   // true
        System.out.println(b.insertFront(100));  // true
        System.out.println(b.insertFront(99));   // false (full)
        System.out.println(b.getRear());         // 102
        System.out.println(b.isFull());          // true
        System.out.println(b.deleteLast());      // true
        System.out.println(b.insertFront(98));   // true
        System.out.println(b.getFront());        // 98
    }
}
