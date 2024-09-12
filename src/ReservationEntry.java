import java.time.LocalDateTime;

public class ReservationEntry {
    private String customerName;
    private LocalDateTime reservationTime;
    private int seatNumber;

    public ReservationEntry(String customerName, LocalDateTime reservationTime, int seatNumber) {
        this.customerName = customerName;
        this.reservationTime = reservationTime;
        this.seatNumber = seatNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "ReservationEntry{" +
                "customerName='" + customerName + '\'' +
                ", reservationTime=" + reservationTime +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
