import java.util.*;

class SKU {
    private String code;
    private String name;
    private int unitCost;
    private int quantity;
    private int reorderPoint;
    private int reorderQty;

    public SKU(String code, String name, int unitCost, int reorderPoint, int reorderQty) {
        this.code = code;
        this.name = name;
        this.unitCost = unitCost;
        this.reorderPoint = reorderPoint;
        this.reorderQty = reorderQty;
        this.quantity = 0; // start empty
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public int getReorderPoint() { return reorderPoint; }
    public int getReorderQty() { return reorderQty; }

    public void addStock(int qty) { quantity += qty; }
    public void removeStock(int qty) { quantity -= qty; }
    public int getStockValue() { return quantity * unitCost; }
}

class Warehouse {
    private ArrayList<SKU> skus = new ArrayList<>();

    public void addSKU(SKU sku) {
        skus.add(sku);
    }

    public SKU findSKU(String code) {
        for (SKU s : skus) {
            if (s.getCode().equals(code)) return s;
        }
        return null;
    }

    public void putAway(String code, int qty) {
        SKU sku = findSKU(code);
        if (sku != null) {
            sku.addStock(qty);
            System.out.println("Put away: " + qty + " units of " + sku.getName());
        } else {
            System.out.println("SKU not found.");
        }
    }

    public void pick(String code, int qty) {
        SKU sku = findSKU(code);
        if (sku != null) {
            if (sku.getQuantity() >= qty) {
                sku.removeStock(qty);
                System.out.println("Picked " + qty + " units of " + sku.getName() +
                                   " | Remaining: " + sku.getQuantity());
            } else {
                System.out.println("Not enough stock!");
            }
        } else {
            System.out.println("SKU not found.");
        }
    }

    public void checkReorder(String code) {
        SKU sku = findSKU(code);
        if (sku != null) {
            if (sku.getQuantity() <= sku.getReorderPoint()) {
                System.out.println("Reorder triggered for " + sku.getName() +
                                   " | Qty: " + sku.getReorderQty());
            } else {
                System.out.println("Stock is healthy for " + sku.getName());
            }
        }
    }

    public void generateReport() {
        int totalValue = 0;
        for (SKU s : skus) {
            totalValue += s.getStockValue();
        }
        System.out.println("=== Warehouse Report ===");
        System.out.println("Total stock value: ₹" + totalValue);
        for (SKU s : skus) {
            System.out.println(s.getCode() + " | " + s.getName() +
                               " | Qty: " + s.getQuantity());
        }
    }
}

public class WarehouseApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Warehouse warehouse = new Warehouse();

        // Add some sample SKUs
        warehouse.addSKU(new SKU("A-231", "Keyboard", 1000, 80, 200));
        warehouse.addSKU(new SKU("B-101", "Mouse", 500, 50, 100));

        while (true) {
            System.out.println("\n=== Warehouse Menu ===");
            System.out.println("1. Put Away");
            System.out.println("2. Pick");
            System.out.println("3. Reorder Check");
            System.out.println("4. Report");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter SKU code: ");
                String code = sc.nextLine();
                System.out.print("Enter quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                warehouse.putAway(code, qty);
            } else if (choice == 2) {
                System.out.print("Enter SKU code: ");
                String code = sc.nextLine();
                System.out.print("Enter quantity: ");
                int qty = Integer.parseInt(sc.nextLine());
                warehouse.pick(code, qty);
            } else if (choice == 3) {
                System.out.print("Enter SKU code: ");
                String code = sc.nextLine();
                warehouse.checkReorder(code);
            } else if (choice == 4) {
                warehouse.generateReport();
            } else if (choice == 5) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }
}
