import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static List<Customer> customers = new ArrayList<>();
    public static List<Bus> buses = new ArrayList<>();
    public static List<Admin> admins = new ArrayList<>();

    static {
        // Add some dummy data for buses
        buses.add(new Bus("bus1", 3, "Batticaloa", "Kandy", LocalTime.of(10, 0), 150.5));
        buses.add(new Bus("bus2", 2, "Colombo", "Trincomalee", LocalTime.of(9, 0), 120.0));
        buses.add(new Bus("bus3", 5, "Matara", "Kandy", LocalTime.of(8, 0), 140.0));
        buses.add(new Bus("bus4", 4, "Jaffna", "Badhula", LocalTime.of(11, 0), 130.0));
        buses.add(new Bus("bus5", 1, "Galle", "Ampara", LocalTime.of(3, 0), 190.0));
    }
}
