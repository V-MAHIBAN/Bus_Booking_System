import java.util.Scanner;

public abstract class User {
    protected static final Scanner scanner = new Scanner(System.in);

    protected String username;
    protected String password;
    protected String name;
    protected String mobile;
    protected String email;
    protected String city;
    protected float age;

    public User() {
    }

    public User(String username, String password, String name, String mobile, String email, String city, float age) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public float getAge() {
        return age;
    }

    public abstract void showMenu();
}
