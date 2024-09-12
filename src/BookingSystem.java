import java.util.InputMismatchException;
import java.util.Scanner;

public class BookingSystem {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu");
            System.out.println("============");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Customer Registration");
            System.out.println("4. Exit");
            System.out.println("Enter your choice:");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    try {
                        if (Admin.authenticate()) {
                            Admin admin = new Admin();
                            admin.showMenu();
                        } else {
                            System.out.println("Invalid admin credentials.");
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred during admin login: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        Customer customer = Customer.authenticate();
                        if (customer != null) {
                            customer.showMenu();
                        } else {
                            System.out.println("Invalid customer credentials.");
                        }
                    } catch (Exception e) {
                        System.out.println("An error occurred during customer login: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        Customer.registerCustomer();
                    } catch (Exception e) {
                        System.out.println("An error occurred during customer registration: " + e.getMessage());
                    }
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    public static int getIntInput() {
        int input = -1;
        while (input == -1) {
            try {
                input = scanner.nextInt();
                scanner.nextLine(); // consume newline character left by nextInt()
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // consume the invalid input
            }
        }
        return input;
    }
}
