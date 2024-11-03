package menus;

import database.CompletedOrders;
import database.DeniedOrders;
import database.ItemList;
import logistics.Category;
import logistics.Item;
import logistics.Order;
import logistics.Status;
import users.Admin;

import java.util.InputMismatchException;

public class AdminMenu extends Menu {
    private final Admin admin;

    public AdminMenu(Admin admin) {
        this.admin = admin;
    }

    public void display() {
        System.out.println("Welcome " + admin.getUsername() + "!");
        System.out.println("• 1 for Adding an item");
        System.out.println("• 2 for Updating existing items");
        System.out.println("• 3 for Removing an item");
        System.out.println("• 4 for Viewing the Pending Orders");
        System.out.println("• 5 for Updating the status of an order");
        System.out.println("• 6 for Processing refunds");
        System.out.println("• 7 for Generating a daily report");
        System.out.println("• 0 for Logging out");
    }

    private void processRefunds() {
        int choice;

        for (int i = 0; i < DeniedOrders.getDeniedOrders().size(); i++) {
            Order o = DeniedOrders.getDeniedOrders().get(i);
            System.out.println(o);
            System.out.println("• 1 for giving the refund");
            System.out.println("• 2 for pausing the refund for this order");
            System.out.println("• 0 to return to menu");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    DeniedOrders.removeDeniedOrder(o);
                    break;
                case 2:
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid input");
                    return;
            }
        }
        System.out.println("Refunds resolved\n");
    }

    private Status inputStatus() {
        System.out.println("• 1 for Delivered");
        System.out.println("• 2 for Out for Delivery");
        System.out.println("• 3 for Preparing");
        System.out.println("• 4 for Order Received");
        System.out.println("• 0 to return to menu");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                return Status.Delivered;
            case 2:
                return Status.OutForDelivery;
            case 3:
                return Status.Preparing;
            case 4:
                return Status.OrderReceived;
            case 0:
                return null;
            default:
                System.out.println("Invalid input");
                return null;
        }
    }

    private Category inputCategory() {
        System.out.println("Choose Category");
        System.out.println("• 1 for Beverages");
        System.out.println("• 2 for Snacks");
        System.out.println("• 3 for Meals");
        System.out.println("• 0 to return to menu");
        int choice = sc.nextInt();
        sc.nextLine();
        switch (choice) {
            case 1:
                return Category.Beverage;
            case 2:
                return Category.Snack;
            case 3:
                return Category.Meal;
            case 0:
                return null;
            default:
                System.out.println("Invalid input");
                return null;
        }
    }

    @Override
    public void mainMenu() {
        int choice;
        int temp;
        Item item;
        String itemName;
        int itemPrice;
        int availability;
        Category category;
        while (true) {
            try {
                this.display();
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        System.out.print("Enter item name: ");
                        itemName = sc.nextLine();
                        System.out.print("Enter item price: ");
                        itemPrice = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter item availability (inventory): ");
                        availability = sc.nextInt();
                        sc.nextLine();
                        category = inputCategory();
                        if (availability == 0 || itemPrice == 0 || itemName == null)
                            System.out.println("Invalid input");
                        else this.admin.addItem(itemName, itemPrice, category, availability);
                        break;
                    case 2:
                        System.out.println("• 1 for updating the price of an item");
                        System.out.println("• 2 for updating the availability of an item");
                        System.out.println("• 0 to return to menu");
                        temp = sc.nextInt();
                        sc.nextLine();
                        switch (temp) {
                            case 1:
                                System.out.print("Enter item name: ");
                                itemName = sc.nextLine();
                                item = ItemList.getItemByName(itemName);
                                if (item == null) {
                                    System.out.println("No such item\n");
                                    break;
                                }
                                System.out.print("Enter item price: ");
                                itemPrice = sc.nextInt();
                                this.admin.updateItemPrice(item, itemPrice);
                                System.out.println("Item updated\n");
                                break;
                            case 2:
                                System.out.print("Enter item name: ");
                                itemName = sc.nextLine();
                                item = ItemList.getItemByName(itemName);
                                if (item == null) {
                                    System.out.println("No such item\n");
                                    break;
                                }
                                System.out.print("Enter item availability (inventory): ");
                                availability = sc.nextInt();
                                this.admin.updateItemAvailability(item, availability);
                                System.out.println("Item updated\n");
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                        break;
                    case 3:
                        System.out.print("Enter item name to remove: ");
                        itemName = sc.nextLine();
                        this.admin.deleteItem(itemName);
                        break;
                    case 4:
                        this.admin.viewPendingOrders();
                        break;
                    case 5:
                        System.out.print("Enter order ID to be updated: ");
                        int ID = sc.nextInt();
                        sc.nextLine();
                        Status status = inputStatus();
                        this.admin.updateOrderStatus(ID, status);
                        break;
                    case 6:
                        this.processRefunds();
                        break;
                    case 7:
                        this.admin.getReport();
                        break;
                    case 0:
                        this.logout();
                        return;
                    default:
                        System.out.println("Invalid input\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input\n");
            }
        }

    }

    private void logout() {
        System.out.println("Logging out...\n");
    }

}
