
import java.util.Scanner;

// Parent class
class Product {
    private String id;
    private String name;
    private String category;

    Product(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getCategory() {
        return category;
    }

    void displayDetails() {
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Category: " + category);
    }
}

// Child class: Inheritance
class WarehouseProduct extends Product {
    private int quantity;
    private int reorderLevel;
    private double price;

    WarehouseProduct(String id, String name, String category,
                     int quantity, int reorderLevel, double price) {
        super(id, name, category);
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.price = price;
    }

    int getQuantity() {
        return quantity;
    }

    int getReorderLevel() {
        return reorderLevel;
    }

    double getPrice() {
        return price;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method overriding
    @Override
    void displayDetails() {
        super.displayDetails();
        System.out.println("Quantity: " + quantity);
        System.out.println("Reorder Level: " + reorderLevel);
        System.out.println("Price: Rs. " + price);

        if (quantity <= reorderLevel) {
            System.out.println("WARNING: Low Stock!");
        } else {
            System.out.println("Stock is available.");
        }
    }
}

public class WarehousePart2 {
    static Scanner sc = new Scanner(System.in);

    // Array of objects
    static WarehouseProduct[] products = new WarehouseProduct[50];
    static int count = 0;

    // Exception handling
    static int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid price.");
            }
        }
    }

    // Method overloading: first method
    static void addProduct() {
        if (count == products.length) {
            System.out.println("Warehouse is full.");
            return;
        }

        System.out.print("Enter Product ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        int quantity = readInt("Enter Quantity: ");
        int reorder = readInt("Enter Reorder Level: ");
        double price = readDouble("Enter Price: ");

        if (quantity < 0 || reorder < 0 || price < 0) {
            System.out.println("Values cannot be negative.");
            return;
        }

        addProduct(id, name, category, quantity, reorder, price);
    }

    // Method overloading: second method
    static void addProduct(String id, String name, String category,
                           int quantity, int reorder, double price) {
        products[count] = new WarehouseProduct(
            id, name, category, quantity, reorder, price
        );
        count++;
        System.out.println("Product added successfully.");
    }

    static void viewProducts() {
        if (count == 0) {
            System.out.println("No products available.");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println("\nProduct " + (i + 1));
            products[i].displayDetails();
            System.out.println("------------------");
        }
    }

    static void pickProduct() {
        System.out.print("Enter Product ID to pick: ");
        String id = sc.nextLine();

        for (int i = 0; i < count; i++) {
            if (products[i].getId().equalsIgnoreCase(id)) {
                int qty = readInt("Enter quantity to pick: ");

                if (qty <= 0) {
                    System.out.println("Enter a positive quantity.");
                } else if (qty <= products[i].getQuantity()) {
                    products[i].setQuantity(
                        products[i].getQuantity() - qty
                    );

                    System.out.println("Product picked successfully.");

                    if (products[i].getQuantity()
                            <= products[i].getReorderLevel()) {
                        System.out.println("WARNING: Reorder this product!");
                    }
                } else {
                    System.out.println("Not enough stock.");
                }
                return;
            }
        }

        System.out.println("Product not found.");
    }

    static void reorderCheck() {
        boolean found = false;

        for (int i = 0; i < count; i++) {
            if (products[i].getQuantity()
                    <= products[i].getReorderLevel()) {
                System.out.println(
                    "WARNING: " + products[i].getName()
                    + " needs reordering."
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("All products have enough stock.");
        }
    }

    static void stockReport() {
        double totalValue = 0;

        for (int i = 0; i < count; i++) {
            totalValue += products[i].getQuantity()
                        * products[i].getPrice();
        }

        System.out.println("Total Products: " + count);
        System.out.println("Total Stock Value: Rs. " + totalValue);
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\nWAREHOUSE INVENTORY SYSTEM");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Pick Product");
            System.out.println("4. Reorder Check");
            System.out.println("5. Stock Report");
            System.out.println("6. Exit");

            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    pickProduct();
                    break;
                case 4:
                    reorderCheck();
                    break;
                case 5:
                    stockReport();
                    break;
                case 6:
                    System.out.println("Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);

        sc.close();
    }
}
