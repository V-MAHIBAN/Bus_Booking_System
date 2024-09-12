import java.util.List;

public class BubbleSort {

    public static void bubbleSort(List<Customer> customers) {
        int n = customers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (customers.get(j).getAge() > customers.get(j + 1).getAge()) {
                    Customer temp = customers.get(j);
                    customers.set(j, customers.get(j + 1));
                    customers.set(j + 1, temp);
                }
            }
        }
    }
}
