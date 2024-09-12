public class Queue {
    private QNode front, rear;

    public Queue() {
        this.front = this.rear = null;
    }

    public void enqueue(ReservationEntry reservation) {
        QNode newNode = new QNode(reservation);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        System.out.println("Added to waiting list: " + reservation.getCustomerName());
    }

    public ReservationEntry dequeue() {
        if (isEmpty()) {
            System.out.println("Waiting list is empty");
            return null;
        }
        ReservationEntry removedReservation = front.reservation;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        System.out.println("Removed from waiting list: " + removedReservation.getCustomerName());
        return removedReservation;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public QNode getFront() {
        return front;
    }
}
