import java.util.Scanner;

public class Admin extends User {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    public Admin() {
    }

    public Admin(String username, String password, String name, String mobile, String email, String city, float age) {
        super(username, password, name, mobile, email, city, age);
    }

    public static boolean authenticate() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    @Override
    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdmin Dashboard");
            System.out.println("================");
            System.out.println("1. Bus Management");
            System.out.println("2. View All Customers");
            System.out.println("3. Manage Customers");
            System.out.println("4. Generate Reports");
            System.out.println("5. System Settings");
            System.out.println("6. Search and Filter Reservations");
            System.out.println("7. Feedback Management");
            System.out.println("8. View Profile");
            System.out.println("9. Change Password");
            System.out.println("10. Logout");
            System.out.print("Enter your choice: ");

            int choice = BookingSystem.getIntInput();

            try {
                switch (choice) {
                    case 1:
                        Bus.showMenu();
                        break;
                    case 2:
                        viewAllCustomers();
                        break;
                    case 3:
                        manageCustomers();
                        break;
                    case 4:
                        generateReports();
                        break;
                    case 5:
                        systemSettings();
                        break;
                    case 6:
                        searchAndFilterReservations();
                        break;
                    case 7:
                        feedbackManagement();
                        break;
                    case 8:
                        viewProfile();
                        break;
                    case 9:
                        changePassword();
                        break;
                    case 10:
                        exit = true;
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 10.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void viewAllCustomers() {
        try {
            System.out.println("\nAll Customers:");
            for (Customer customer : DB.customers) {
                System.out.println(customer);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing customers: " + e.getMessage());
        }
    }

    private void manageCustomers() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nManage Customers");
            System.out.println("=================");
            System.out.println("1. Update Customer");
            System.out.println("2. Delete Customer");
            System.out.println("3. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");

            int choice = BookingSystem.getIntInput();

            try {
                switch (choice) {
                    case 1:
                        updateCustomer();
                        break;
                    case 2:
                        deleteCustomer();
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void updateCustomer() {
        try {
            System.out.print("Enter Customer Username to update: ");
            String username = scanner.nextLine();
            Customer customer = DB.customers.stream()
                                            .filter(c -> c.getUsername().equals(username))
                                            .findFirst()
                                            .orElse(null);

            if (customer != null) {
                System.out.print("Enter new name (leave blank to keep current): ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    customer.setName(newName);
                }

                System.out.print("Enter new mobile (leave blank to keep current): ");
                String newMobile = scanner.nextLine();
                if (!newMobile.isEmpty()) {
                    customer.setMobile(newMobile);
                }

                System.out.print("Enter new email (leave blank to keep current): ");
                String newEmail = scanner.nextLine();
                if (!newEmail.isEmpty()) {
                    customer.setEmail(newEmail);
                }

                System.out.print("Enter new city (leave blank to keep current): ");
                String newCity = scanner.nextLine();
                if (!newCity.isEmpty()) {
                    customer.setCity(newCity);
                }

                System.out.print("Enter new age (leave blank to keep current): ");
                String newAge = scanner.nextLine();
                if (!newAge.isEmpty()) {
                    customer.setAge(Float.parseFloat(newAge));
                }

                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid age format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println("An error occurred while updating customer: " + e.getMessage());
        }
    }

    private void deleteCustomer() {
        try {
            System.out.print("Enter Customer Username to delete: ");
            String username = scanner.nextLine();
            Customer customer = DB.customers.stream()
                                            .filter(c -> c.getUsername().equals(username))
                                            .findFirst()
                                            .orElse(null);

            if (customer != null) {
                DB.customers.remove(customer);
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while deleting customer: " + e.getMessage());
        }
    }

    private void generateReports() {
        try {
            System.out.println("\nGenerate Reports");
            System.out.println("================");
            System.out.println("1. Daily Bookings Report");
            System.out.println("2. Cancellations Report");
            System.out.println("3. Revenue Report");
            System.out.println("4. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");

            int choice = BookingSystem.getIntInput();

            switch (choice) {
                case 1:
                    generateDailyBookingsReport();
                    break;
                case 2:
                    generateCancellationsReport();
                    break;
                case 3:
                    generateRevenueReport();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while generating reports: " + e.getMessage());
        }
    }

    private void generateDailyBookingsReport() {
        try {
            System.out.println("\nDaily Bookings Report:");
            for (Bus bus : DB.buses) {
                System.out.println("Bus Number: " + bus.getBusNumber());
                for (ReservationEntry reservation : bus.getReservations()) {
                    System.out.println(reservation);
                }
                System.out.println("---------------------------");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while generating daily bookings report: " + e.getMessage());
        }
    }

    private void generateCancellationsReport() {
        // Placeholder for cancellations report
        System.out.println("Cancellations Report feature coming soon...");
    }

    private void generateRevenueReport() {
        // Placeholder for revenue report
        System.out.println("Revenue Report feature coming soon...");
    }

    private void systemSettings() {
        try {
            System.out.println("\nSystem Settings");
            System.out.println("================");
            System.out.println("1. Notification Settings");
            System.out.println("2. Fare Adjustments");
            System.out.println("3. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");

            int choice = BookingSystem.getIntInput();

            switch (choice) {
                case 1:
                    configureNotificationSettings();
                    break;
                case 2:
                    adjustFares();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while configuring system settings: " + e.getMessage());
        }
    }

    private void configureNotificationSettings() {
        // Placeholder for notification settings
        System.out.println("Notification Settings feature coming soon...");
    }

    private void adjustFares() {
        // Placeholder for fare adjustments
        System.out.println("Fare Adjustments feature coming soon...");
    }

    private void searchAndFilterReservations() {
        try {
            System.out.println("\nSearch and Filter Reservations");
            System.out.println("==============================");
            System.out.print("Enter criteria (bus number, customer name, etc.): ");
            String criteria = scanner.nextLine();

            for (Bus bus : DB.buses) {
                if (bus.getBusNumber().equalsIgnoreCase(criteria) || bus.getReservations().stream().anyMatch(r -> r.getCustomerName().equalsIgnoreCase(criteria))) {
                    System.out.println("Bus Number: " + bus.getBusNumber());
                    for (ReservationEntry reservation : bus.getReservations()) {
                        if (reservation.getCustomerName().equalsIgnoreCase(criteria) || String.valueOf(reservation.getSeatNumber()).equals(criteria)) {
                            System.out.println(reservation);
                        }
                    }
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while searching and filtering reservations: " + e.getMessage());
        }
    }

    private void feedbackManagement() {
        // Placeholder for feedback management
        System.out.println("Feedback Management feature coming soon...");
    }

    private void viewProfile() {
        try {
            System.out.println("\nAdmin Profile:");
            System.out.println(this);
        } catch (Exception e) {
            System.out.println("An error occurred while viewing profile: " + e.getMessage());
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

    @Override
    public String toString() {
        return "Admin\n" +
                "======================\n" +
                "Username   : " + username + '\n' +
                "Name       : " + name + '\n' +
                "Mobile     : " + mobile + '\n' +
                "E-mail     : " + email + '\n' +
                "City       : " + city + '\n' +
                "Age        : " + age;
    }
}
