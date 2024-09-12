import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Customer extends User {

    private List<ReservationEntry> reservationHistory;

    public Customer() {
        super();
        this.reservationHistory = new ArrayList<>();
    }

    public Customer(String username, String password, String name, String mobile, String email, String city, float age) {
        super(username, password, name, mobile, email, city, age);
        this.reservationHistory = new ArrayList<>();
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public static Customer authenticate() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        for (Customer customer : DB.customers) {
            if (customer.username.equals(username) && customer.password.equals(password)) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nCustomer Dashboard");
            System.out.println("==================");
            System.out.println("1. Search for Buses");
            System.out.println("2. Reserve Seat");
            System.out.println("3. Cancel Seat Reservation");
            System.out.println("4. View Seat Availability");
            System.out.println("5. View Reservation History");
            System.out.println("6. View Profile");
            System.out.println("7. Update Profile");
            System.out.println("8. Change Password");
            System.out.println("9. View Notifications");
            System.out.println("10. Provide Feedback");
            System.out.println("11. Logout");
            System.out.print("Enter your choice: ");

            int choice = BookingSystem.getIntInput();

            try {
                switch (choice) {
                    case 1:
                        Bus.searchBusesByRouteAndTime();
                        break;
                    case 2:
                        reserveSeat();
                        break;
                    case 3:
                        cancelSeatReservation();
                        break;
                    case 4:
                        viewSeatAvailability();
                        break;
                    case 5:
                        viewReservationHistory();
                        break;
                    case 6:
                        viewProfile();
                        break;
                    case 7:
                        updateProfile();
                        break;
                    case 8:
                        changePassword();
                        break;
                    case 9:
                        viewNotifications();
                        break;
                    case 10:
                        provideFeedback();
                        break;
                    case 11:
                        exit = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 11.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    public static void registerCustomer() {
        Customer customer = new Customer();
        try {
            System.out.print("Enter Customer Name: ");
            customer.name = scanner.nextLine();

            System.out.print("Enter Customer Mobile: ");
            customer.mobile = scanner.nextLine();

            System.out.print("Enter Customer Email: ");
            customer.email = scanner.nextLine();

            System.out.print("Enter Customer City: ");
            customer.city = scanner.nextLine();

            System.out.print("Enter Customer Age: ");
            customer.age = Float.parseFloat(scanner.nextLine());

            System.out.print("Enter Username: ");
            customer.username = scanner.nextLine();

            System.out.print("Enter Password: ");
            customer.password = scanner.nextLine();

            DB.customers.add(customer);
            System.out.println("Customer registered successfully");
        } catch (NumberFormatException e) {
            System.out.println("Invalid age format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred during registration: " + e.getMessage());
        }
    }

    private void viewReservationHistory() {
        try {
            System.out.println("\nReservation History:");
            for (ReservationEntry reservation : reservationHistory) {
                System.out.println(reservation);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing reservation history: " + e.getMessage());
        }
    }

    public void addReservationToHistory(ReservationEntry reservation) {
        reservationHistory.add(reservation);
    }

    private void reserveSeat() {
        try {
            System.out.print("Enter Bus Number: ");
            String busNumber = scanner.nextLine();
            Bus bus = Bus.searchBusByNumber(busNumber);

            if (bus != null) {
                System.out.print("Enter Seat Number: ");
                int seatNumber = scanner.nextInt();
                scanner.nextLine(); // consume newline

                LocalDateTime now = LocalDateTime.now();
                ReservationEntry reservation = new ReservationEntry(this.name, now, seatNumber);

                if (bus.getReservations().size() < bus.getTotalSeats() && bus.getReservations().stream().noneMatch(r -> r.getSeatNumber() == seatNumber)) {
                    bus.getReservations().add(reservation);
                    this.addReservationToHistory(reservation);
                    System.out.println("Seat " + seatNumber + " reserved successfully for " + this.name);
                    sendNotification("Seat reserved", "Your seat " + seatNumber + " has been reserved successfully.", this.name);
                } else {
                    bus.getWaitingList().enqueue(reservation);
                    System.out.println("Bus is full. Added " + this.name + " to the waiting list for seat " + seatNumber);
                    sendNotification("Added to waiting list", "The bus is full. You have been added to the waiting list.", this.name);
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

    private void cancelSeatReservation() {
        try {
            System.out.print("Enter Bus Number: ");
            String busNumber = scanner.nextLine();
            Bus bus = Bus.searchBusByNumber(busNumber);

            if (bus != null) {
                boolean found = false;
                for (ReservationEntry reservation : bus.getReservations()) {
                    if (reservation.getCustomerName().equalsIgnoreCase(this.name)) {
                        bus.getReservations().remove(reservation);
                        found = true;
                        System.out.println("Reservation cancelled for " + this.name);
                        sendNotification("Reservation cancelled", "Your reservation for seat " + reservation.getSeatNumber() + " has been cancelled.", this.name);
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Reservation not found for " + this.name);
                }

                bus.notifyWaitingList();
            } else {
                System.out.println("Bus not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while cancelling the reservation: " + e.getMessage());
        }
    }

    private void viewSeatAvailability() {
        try {
            System.out.print("Enter Bus Number: ");
            String busNumber = scanner.nextLine();
            Bus bus = Bus.searchBusByNumber(busNumber);

            if (bus != null) {
                System.out.println("\nSeat Availability for Bus " + busNumber + ":");
                for (int i = 1; i <= bus.getTotalSeats(); i++) {
                    int seatNumber = i;
                    boolean isReserved = bus.getReservations().stream().anyMatch(r -> r.getSeatNumber() == seatNumber);
                    System.out.println("Seat " + seatNumber + ": " + (isReserved ? "Reserved" : "Available"));
                }
            } else {
                System.out.println("Bus not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing seat availability: " + e.getMessage());
        }
    }

    private void viewProfile() {
        try {
            System.out.println("\nCustomer Profile:");
            System.out.println(this);
        } catch (Exception e) {
            System.out.println("An error occurred while viewing profile: " + e.getMessage());
        }
    }

    private void updateProfile() {
        try {
            System.out.print("Enter new name (leave blank to keep current): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                this.name = newName;
            }

            System.out.print("Enter new mobile (leave blank to keep current): ");
            String newMobile = scanner.nextLine();
            if (!newMobile.isEmpty()) {
                this.mobile = newMobile;
            }

            System.out.print("Enter new email (leave blank to keep current): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                this.email = newEmail;
            }

            System.out.print("Enter new city (leave blank to keep current): ");
            String newCity = scanner.nextLine();
            if (!newCity.isEmpty()) {
                this.city = newCity;
            }

            System.out.print("Enter new age (leave blank to keep current): ");
            String newAge = scanner.nextLine();
            if (!newAge.isEmpty()) {
                this.age = Float.parseFloat(newAge);
            }

            System.out.println("Profile updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid age format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while updating profile: " + e.getMessage());
        }
    }

    private void changePassword() {
        try {
            System.out.print("Enter current password: ");
            String currentPassword = scanner.nextLine();
            if (currentPassword.equals(this.password)) {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                this.password = newPassword;
                System.out.println("Password changed successfully.");
            } else {
                System.out.println("Current password is incorrect.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while changing password: " + e.getMessage());
        }
    }

    private void viewNotifications() {
        // This is a placeholder for the notification feature.
        try {
            System.out.println("No new notifications.");
        } catch (Exception e) {
            System.out.println("An error occurred while viewing notifications: " + e.getMessage());
        }
    }

    private void provideFeedback() {
        try {
            System.out.print("Enter your feedback: ");
            String feedback = scanner.nextLine();
            // Here you would save the feedback to a database or send it to support.
            System.out.println("Thank you for your feedback.");
        } catch (Exception e) {
            System.out.println("An error occurred while providing feedback: " + e.getMessage());
        }
    }

    private static void sendNotification(String subject, String message, String customerName) {
        System.out.println("Notification sent to " + customerName + ": [" + subject + "] " + message);
    }

    @Override
    public String toString() {
        return "Customer\n" +
               "======================\n" +
               "Username   : " + username + '\n' +
               "Name       : " + name + '\n' +
               "Mobile     : " + mobile + '\n' +
               "E-mail     : " + email + '\n' +
               "City       : " + city + '\n' +
               "Age        : " + age;
    }
}
