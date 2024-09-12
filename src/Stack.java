public class Stack {

    private int top;
    private int capacity;
    private Customer[] stack;

    public Stack(int size) {
        this.top = -1;
        this.capacity = size;
        this.stack = new Customer[capacity];
    }

    public void push(Customer item) {
        if (top == capacity - 1) {
            System.out.println("Stack is full");
            return;
        }
        stack[++top] = item;
    }

    public Customer pop() {
        if (top == -1) {
            System.out.println("Stack is empty");
            return null;
        }
        return stack[top--];
    }

    public void display() {
        if (top == -1) {
            System.out.println("Stack is empty");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("%s, ", stack[i].toString());
        }
        System.out.println();
    }

    public void displayTop() {
        if (top == -1) {
            System.out.println("Stack is empty");
            return;
        }
        System.out.printf("Top element of the stack: %s\n", stack[top].toString());
    }
}
