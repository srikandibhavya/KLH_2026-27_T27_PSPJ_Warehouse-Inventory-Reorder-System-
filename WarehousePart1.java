
import java.util.Scanner;

public class WarehousePart1 {
    static Scanner sc = new Scanner(System.in);

    static String id, name, category;
    static int quantity, reorderLevel;
    static double price;
    static boolean productAdded = false;

    // Method to add a product
    static void addProduct() {
        System.out.print("Enter Product ID: ");
        id = sc.nextLine();

        System.out.print("Enter Product Name: ");
        name = sc.nextLine();

        System.out.print("Enter Category: ");
        category = sc.nextLine();

        System.out.print("Enter Quantity: ");
        quantity = sc.nextInt();

        System.out.print("Enter Reorder Level: ");
        reorderLevel = sc.nextInt();

        System.out.print("Enter Price: ");
        price = sc.nextDouble();
        sc.nextLine();

        if (quantity < 0 || reorderLevel < 0 || price < 0) {
            System.out.println("Invalid values.");
            return;
        }

        productAdded = true;
        System.out.println("Product added successfully.");
    }

    // Method to display product details
    static void displayProduct() {
        if (!productAdded) {
            System.out.println("No product available.");
            return;
        }

        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: Rs. " + price);

        if (quantity <= reorderLevel) {
            System.out.println("WARNING: Low Stock!");
        } else {
            System.out.println("Stock is available.");
        }
    }

    // Method to pick a product
    static void pickProduct() {
        if (!productAdded) {
            System.out.println("Add a product first.");
            return;
        }

        System.out.print("Enter quantity to pick: ");
        int qty = sc.nextInt();
        sc.nextLine();

        if (qty <= 0) {
            System.out.println("Enter a positive quantity.");
        } else if (qty <= quantity) {
            quantity = quantity - qty;
            System.out.println("Product picked successfully.");

            if (quantity <= reorderLevel) {
                System.out.println("WARNING: Reorder this product!");
            }
        } else {
            System.out.println("Not enough stock.");
        }
    }

    // Method to check stock
    static void reorderCheck() {
        if (!productAdded) {
            System.out.println("Add a product first.");
        } else if (quantity <= reorderLevel) {
            System.out.println("WARNING: Reorder " + name);
        } else {
            System.out.println("Stock is sufficient.");
        }
    }

    // Method to calculate stock value
    static void stockReport() {
        if (!productAdded) {
            System.out.println("No product available.");
            return;
        }

        double totalValue = quantity * price;

        System.out.println("Product: " + name);
        System.out.println("Remaining Quantity: " + quantity);
        System.out.println("Total Stock Value: Rs. " + totalValue);
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\nWAREHOUSE INVENTORY SYSTEM");
            System.out.println("1. Add Product");
            System.out.println("2. View Product");
            System.out.println("3. Pick Product");
            System.out.println("4. Reorder Check");
            System.out.println("5. Stock Report");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                addProduct();
            } else if (choice == 2) {
                displayProduct();
            } else if (choice == 3) {
                pickProduct();
            } else if (choice == 4) {
                reorderCheck();
            } else if (choice == 5) {
                stockReport();
            } else if (choice == 6) {
                System.out.println("Thank you!");
            } else {
                System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}
 
    

