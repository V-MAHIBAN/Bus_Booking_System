import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Bus {

    private String busNumber;
    private int totalSeats;
    private String startingPoint;
    private String endingPoint;
    private LocalTime startingTime;
    private double fare;
    private List<ReservationEntry> reservations;
    private Queue waitingList;

    private static Scanner scanner = new Scanner(System.in);

    public Bus() {
        this.reservations = new ArrayList<>();
        this.waitingList = new Queue();
    }

    public Bus(String busNumber, int totalSeats, String startingPoint, String endingPoint, LocalTime startingTime, double fare) {
        this.busNumber = busNumber;
        this.totalSeats = totalSeats;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.startingTime = startingTime;
        this.fare = fare;
        this.reservations = new ArrayList<>();
        this.waitingList = new Queue();
    }

    // Getters and setters

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public List<ReservationEntry> getReservations() {
        return reservations;
    }

    public Queue getWaitingList() {
        return waitingList;
    }

    public static void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nHome > Bus Management");
            System.out.println("==================");
            System.out.println("1. Register Bus");
            System.out.println("2. Update Bus");
            System.out.println("3. Delete Bus");
            System.out.println("4. View All Buses");
            System.out.println("5. Search Buses by Route and Time");
            System.out.println("6. Reserve Seat");
            System.out.println("7. Cancel Reservation");
            System.out.println("8. Promote from Waiting List");
            System.out.println("9. View All Customer Reservation Details");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerBus();
                        break;
                    case 2:
                        updateBus();
                        break;
                    case 3:
                        deleteBus();
                        break;
                    case 4:
                        viewAllBuses();
                        break;
                    case 5:
                        searchBusesByRouteAndTime();
                        break;
                    case 6:
                        reserveSeat();
                        break;
                    case 7:
                        cancelReservation();
                        break;
                    case 8:
                        promoteFromWaitingList();
                        break;
                    case 9:
                        viewAllCustomerReservationDetails();
                        break;
                    case 10:
                        exit = true;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // clear the invalid input from the scanner
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please enter time in HH:mm format.");
                scanner.nextLine(); // clear the invalid input from the scanner
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    public static void registerBus() {
        try {
            Bus bus = new Bus();
            System.out.print("Enter Bus Number: ");
            bus.setBusNumber(scanner.nextLine());

            System.out.print("Enter Total Seats: ");
            bus.setTotalSeats(Integer.parseInt(scanner.nextLine()));

            System.out.print("Enter Starting Point: ");
            bus.setStartingPoint(scanner.nextLine());

            System.out.print("Enter Ending Point: ");
            bus.setEndingPoint(scanner.nextLine());

            System.out.print("Enter Starting Time (HH:mm): ");
            String startTimeStr = scanner.nextLine();
            bus.setStartingTime(LocalTime.parse(startTimeStr));

            System.out.print("Enter Fare: ");
            bus.setFare(Double.parseDouble(scanner.nextLine()));
            DB.buses.add(bus);
            System.out.println("Bus registered successfully");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please enter time in HH:mm format.");
        } catch (Exception e) {
            System.out.println("An error occurred during bus registration: " + e.getMessage());
        }
    }

    public static void updateBus() {
        try {
            System.out.print("Enter Bus Number to update: ");
            String findBusNumber = scanner.nextLine();
            Bus bus = searchBusByNumber(findBusNumber);

            if (bus != null) {
                System.out.print("Enter New Bus Number: ");
                bus.setBusNumber(scanner.nextLine());

                System.out.print("Enter Total Seats: ");
                bus.setTotalSeats(Integer.parseInt(scanner.nextLine()));

                System.out.print("Enter Starting Point: ");
                bus.setStartingPoint(scanner.nextLine());

                System.out.print("Enter Ending Point: ");
                bus.setEndingPoint(scanner.nextLine());

                System.out.print("Enter Starting Time (HH:mm): ");
                String startTimeStr = scanner.nextLine();
                bus.setStartingTime(LocalTime.parse(startTimeStr));

                System.out.print("Enter Fare: ");
                bus.setFare(Double.parseDouble(scanner.nextLine()));

                System.out.println("Bus updated successfully");
            } else {
                System.out.println("Bus not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please enter time in HH:mm format.");
        } catch (Exception e) {
            System.out.println("An error occurred during bus update: " + e.getMessage());
        }
    }

    public static void deleteBus() {
        try {
            System.out.print("Enter Bus Number to delete: ");
            String findBusNumber = scanner.nextLine();
            Bus bus = searchBusByNumber(findBusNumber);

            if (bus != null) {
                DB.buses.remove(bus);
                System.out.println("Bus deleted successfully");
            } else {
                System.out.println("Bus not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during bus deletion: " + e.getMessage());
        }
    }

    public static void reserveSeat() {
        try {
            System.out.print("Enter Bus Number: ");
            String busNumber = scanner.nextLine();
            Bus bus = searchBusByNumber(busNumber);

            if (bus != null) {
                System.out.print("Enter Customer Name: ");
                String customerName = scanner.nextLine();

                System.out.print("Enter Seat Number: ");
                int seatNumber = scanner.nextInt();
                scanner.nextLine(); // consume newline

                LocalDateTime now = LocalDateTime.now();
                ReservationEntry reservation = new ReservationEntry(customerName, now, seatNumber);

                if (bus.getReservations().size() < bus.getTotalSeats() && bus.getReservations().stream().noneMatch(r -> r.getSeatNumber() == seatNumber)) {
                    bus.getReservations().add(reservation);
                    DB.customers.stream().filter(c -> c.getName().equals(customerName)).findFirst().ifPresent(c -> c.addReservationToHistory(reservation));
                    System.out.println("Seat " + seatNumber + " reserved successfully for " + customerName);
                    sendNotification("Seat reserved", "Your seat " + seatNumber + " has been reserved successfully.", customerName);
                } else {
                    bus.getWaitingList().enqueue(reservation);
                    System.out.println("Bus is full. Added " + customerName + " to the waiting list for seat " + seatNumber);
                    sendNotification("Added to waiting list", "The bus is full. You have been added to the waiting list.", customerName);
                }
            } else {
                System.out.println("Bus not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid seat number format. Please enter a valid number.");
            scanner.nextLine(); // consume invalid input
        } catch (Exception e) {
            System.out.println("An error occurred while reserving the seat: " + e.getMessage());
        }
    }

    public static void cancelReservation() {
        try {
            System.out.print("Enter Bus Number: ");
            String busNumber = scanner.nextLine();
            Bus bus = searchBusByNumber(busNumber);

            if (bus != null) {
                System.out.print("Enter Customer Name to cancel reservation: ");
                String customerName = scanner.nextLine();

                boolean found = false;
                for (ReservationEntry reservation : bus.getReservations()) {
                    if (reservation.getCustomerName().equalsIgnoreCase(customerName)) {
                        bus.getReservations().remove(reservation);
                        found = true;
                        System.out.println("Reservation cancelled for " + customerName);
                        sendNotification("Reservation cancelled", "Your reservation for seat " + reservation.getSeatNumber() + " has been cancelled.", customerName);
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Reservation not found for " + customerName);
                }

                bus.notifyWaitingList();
            } else {
                System.out.println("Bus not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while cancelling the reservation: " + e.getMessage());
        }
    }

    public static void promoteFromWaitingList() {
        try {
            System.out.print("Enter Bus Number: ");
            String busNumber = scanner.nextLine();
            Bus bus = searchBusByNumber(busNumber);

            if (bus != null) {
                if (!bus.getWaitingList().isEmpty()) {
                    ReservationEntry nextReservation = bus.getWaitingList().dequeue();
                    bus.getReservations().add(nextReservation);
                    DB.customers.stream().filter(c -> c.getName().equals(nextReservation.getCustomerName())).findFirst().ifPresent(c -> c.addReservationToHistory(nextReservation));
                    System.out.println("Promoted " + nextReservation.getCustomerName() + " from waiting list");
                    sendNotification("Promoted from waiting list", "You have been promoted from the waiting list for seat " + nextReservation.getSeatNumber(), nextReservation.getCustomerName());
                } else {
                    System.out.println("Waiting list is empty");
                }
            } else {
                System.out.println("Bus not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while promoting from the waiting list: " + e.getMessage());
        }
    }

    private static void sendNotification(String subject, String message, String customerName) {
        System.out.println("Notification sent to " + customerName + ": [" + subject + "] " + message);
    }

    public void notifyWaitingList() {
        try {
            if (!waitingList.isEmpty()) {
                ReservationEntry nextReservation = waitingList.dequeue();
                reservations.add(nextReservation);
                DB.customers.stream().filter(c -> c.getName().equals(nextReservation.getCustomerName())).findFirst().ifPresent(c -> c.addReservationToHistory(nextReservation));
                System.out.println("Notified " + nextReservation.getCustomerName() + " from waiting list for seat " + nextReservation.getSeatNumber());
                sendNotification("Seat available", "A seat has become available. Your reservation for seat " + nextReservation.getSeatNumber() + " is confirmed.", nextReservation.getCustomerName());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while notifying the waiting list: " + e.getMessage());
        }
    }

    public static Bus searchBusByNumber(String busNumber) {
        for (Bus bus : DB.buses) {
            if (bus.getBusNumber().equalsIgnoreCase(busNumber)) {
                return bus;
            }
        }
        return null;
    }

    public static List<Bus> searchBusesByRouteAndTime() {
        try {
            System.out.print("Enter Starting Point: ");
            String startingPoint = scanner.nextLine();

            System.out.print("Enter Ending Point: ");
            String endingPoint = scanner.nextLine();

            System.out.print("Enter Starting Time (HH:mm): ");
            String startTimeStr = scanner.nextLine();

            LocalTime startingTime = LocalTime.parse(startTimeStr);
            List<Bus> foundBuses = new ArrayList<>();

            for (Bus bus : DB.buses) {
                if (bus.getStartingPoint().equalsIgnoreCase(startingPoint) &&
                        bus.getEndingPoint().equalsIgnoreCase(endingPoint) &&
                        bus.getStartingTime().equals(startingTime)) {
                    foundBuses.add(bus);
                }
            }

            if (foundBuses.isEmpty()) {
                System.out.println("\nNo buses found for route " + startingPoint + " to " + endingPoint + " at " + startTimeStr);
            } else {
                System.out.println("\nBuses found for route " + startingPoint + " to " + endingPoint + " at " + startTimeStr + ":");
                for (Bus bus : foundBuses) {
                    System.out.println(bus);
                }
            }

            return foundBuses;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please enter time in HH:mm format.");
        } catch (Exception e) {
            System.out.println("An error occurred while searching buses by route and time: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void viewAllBuses() {
        try {
            System.out.println("\nAll Buses:");
            for (Bus bus : DB.buses) {
                System.out.println(bus);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing all buses: " + e.getMessage());
        }
    }

    public static void viewAllCustomerReservationDetails() {
        try {
            System.out.println("\nAll Customer Reservation Details:");
            for (Bus bus : DB.buses) {
                System.out.println("Bus Number: " + bus.getBusNumber());
                for (ReservationEntry reservation : bus.getReservations()) {
                    System.out.println(reservation);
                }
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing all customer reservation details: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Bus\n" +
                "======================\n" +
                "Bus Number    : " + busNumber + '\n' +
                "Total Seats   : " + totalSeats + '\n' +
                "Starting Point: " + startingPoint + '\n' +
                "Ending Point  : " + endingPoint + '\n' +
                "Starting Time : " + startingTime + '\n' +
                "Fare          : " + fare;
    }
}
