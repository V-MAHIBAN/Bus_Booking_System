public class QNode {
    public ReservationEntry reservation;
    public QNode next;

    public QNode(ReservationEntry reservation) {
        this.reservation = reservation;
        this.next = null;
    }
}
