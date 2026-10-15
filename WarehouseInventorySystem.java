
    
import java.util.Scanner;

class Product {
    String id;
    String name;
    String category;

    Product(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
    }
}

// Inheritance: WarehouseProduct inherits Product
class WarehouseProduct extends Product {
    int quantity;
    int reorderLevel;
    double price;

    WarehouseProduct(String id, String name, String category,
                     int quantity, int reorderLevel, double price) {
        super(id, name, category);
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.price = price;
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
            System.out.println("Stock is Available.");
        }
    }
}

public class WarehouseInventorySystem {
    static Scanner sc = new Scanner(System.in);

    WarehouseProduct[] products = new WarehouseProduct[50];
    int count = 0;

    // Exception handling for integer input
    int readInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    // Exception handling for decimal input
    double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid price.");
            }
        }
    }

    // Method overloading: first method
    void addProduct() {
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
    void addProduct(String id, String name, String category,
                    int quantity, int reorder, double price) {
        products[count] = new WarehouseProduct(
            id, name, category, quantity, reorder, price
        );
        count++;

        System.out.println("Product added successfully.");
    }

    void displayProducts() {
        if (count == 0) {
            System.out.println("No products available.");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println("\nProduct " + (i + 1));
            products[i].displayDetails();
            System.out.println("--------------------");
        }
    }

    void pickProduct() {
        System.out.print("Enter Product ID: ");
        String id = sc.nextLine();

        for (int i = 0; i < count; i++) {
            if (products[i].id.equalsIgnoreCase(id)) {
                int qty = readInt("Enter quantity to pick: ");

                if (qty <= 0) {
                    System.out.println("Enter a positive quantity.");
                } else if (qty <= products[i].quantity) {
                    products[i].quantity =
                        products[i].quantity - qty;

                    System.out.println("Product picked successfully.");

                    if (products[i].quantity <=
                        products[i].reorderLevel) {
                        System.out.println("WARNING: Reorder this product!");
                    }
                } else {
                    System.out.println("Not enough stock available.");
                }
                return;
            }
        }

        System.out.println("Product not found.");
    }

    void reorderCheck() {
        boolean found = false;

        for (int i = 0; i < count; i++) {
            if (products[i].quantity <= products[i].reorderLevel) {
                System.out.println(
                    "WARNING: " + products[i].name + " needs reordering."
                );
                found = true;
            }
        }

        if (!found) {
            System.out.println("All products have enough stock.");
        }
    }

    void stockReport() {
        double totalValue = 0;

        for (int i = 0; i < count; i++) {
            totalValue = totalValue +
                (products[i].quantity * products[i].price);
        }

        System.out.println("Total Products: " + count);
        System.out.println("Total Stock Value: Rs. " + totalValue);
    }

    public static void main(String[] args) {
        WarehouseInventorySystem w =
            new WarehouseInventorySystem();

        int choice;

        do {
            System.out.println("\n=== WAREHOUSE INVENTORY SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Pick Product");
            System.out.println("4. Reorder Check");
            System.out.println("5. Stock Report");
            System.out.println("6. Exit");

            choice = w.readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    w.addProduct();
                    break;

                case 2:
                    w.displayProducts();
                    break;

                case 3:
                    w.pickProduct();
                    break;

                case 4:
                    w.reorderCheck();
                    break;

                case 5:
                    w.stockReport();
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


