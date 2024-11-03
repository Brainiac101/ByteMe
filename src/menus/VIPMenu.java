package menus;

import database.CustomerList;
import logistics.Category;
import users.VIP;

import java.util.InputMismatchException;

public class VIPMenu extends CustomerMenu {
    public VIPMenu(VIP customer) {
        super(customer);
    }

    private void display() {
        System.out.println("Welcome " + customer.getUsername() + "!");
        System.out.println("You have VIP privileges, your orders will be given priority over others");
        System.out.println("• 1 for Viewing All Items");
        System.out.println("• 2 for Searching a specific Item");
        System.out.println("• 3 for Filtering based on Category");
        System.out.println("• 4 for Sorting items based on price");
        System.out.println("• 5 for adding items to the cart");
        System.out.println("• 6 for modifying quantity of an item in the cart");
        System.out.println("• 7 for removing items from the cart");
        System.out.println("• 8 for viewing the current contents of the cart");
        System.out.println("• 9 for placing the order");
        System.out.println("• 10 for viewing order status");
        System.out.println("• 11 for cancelling an order");
        System.out.println("• 12 for viewing your order history");
        System.out.println("• 13 for re-ordering a previously placed order");
        System.out.println("• 14 for Writing a review to an item");
        System.out.println("• 15 for Reading reviews of an item");
        System.out.println("• 0 for Logging out");
    }

    @Override
    public void mainMenu() {
        int choice;
        String itemName;
        int temp;
        while (true) {
            try {
                this.display();
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        this.customer.viewMenu();
                        break;
                    case 2:
                        System.out.print("Enter name of the item: ");
                        itemName = sc.nextLine();
                        this.customer.searchByName(itemName);
                        break;
                    case 3:
                        Category category = this.inputCategory();
                        if (category == null) continue;
                        this.customer.searchByCategory(category);
                        break;
                    case 4:
                        System.out.println("• 1 for viewing all items in ascending order (by prices)");
                        System.out.println("• 2 for viewing all items in descending order (by prices)");
                        System.out.println("• 0 to return to menu");
                        temp = sc.nextInt();
                        sc.nextLine();
                        switch (temp) {
                            case 1:
                                this.customer.getItemsByIncreasingPrice();
                                break;
                            case 2:
                                this.customer.getItemsByDecreasingPrice();
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid input\n");
                        }
                        break;
                    case 5:
                        System.out.print("Enter name of the item: ");
                        itemName = sc.nextLine();
                        System.out.print("Enter quantity required: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();
                        this.customer.addItem(itemName, quantity);
                        break;
                    case 6:
                        this.customer.viewCart();
                        System.out.println("• 1 for increasing an item's quantity by 1");
                        System.out.println("• 2 for decreasing an item's quantity by 1");
                        System.out.println("• 0 to return to menu");
                        temp = sc.nextInt();
                        sc.nextLine();
                        switch (temp) {
                            case 1:
                                System.out.print("Enter item name: ");
                                itemName = sc.nextLine();
                                this.customer.increaseQuantity(itemName);
                                break;
                            case 2:
                                System.out.print("Enter item name: ");
                                itemName = sc.nextLine();
                                this.customer.decreaseQuantity(itemName);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid input\n");
                                break;
                        }
                        break;
                    case 7:
                        this.customer.viewCart();
                        System.out.print("Enter item name to remove: ");
                        itemName = sc.nextLine();
                        this.customer.removeItem(itemName);
                        break;
                    case 8:
                        this.customer.viewCart();
                        break;
                    case 9:
                        System.out.println("• 1 for adding specifications to the order");
                        System.out.println("• 2 for directly placing the order");
                        System.out.println("• 0 to return to menu");
                        temp = sc.nextInt();
                        sc.nextLine();
                        switch (temp) {
                            case 1:
                                System.out.print("Enter specifications/requests: ");
                                String specifications = sc.nextLine();
                                this.customer.addSpecification(specifications);
                            case 2:
                                this.placeOrder();
                                break;
                        }
                        break;
                    case 10:
                        System.out.println("Searching for recent orders...");
                        this.customer.viewStatus();
                        break;
                    case 11:
                        this.customer.cancelOrder();
                        break;
                    case 12:
                        this.customer.viewOrderHistory();
                        break;
                    case 13:
                        this.customer.viewOrderHistory();
                        System.out.print("Enter index of the order to repeat (the index is mentioned as a bullet point): ");
                        int orderIndex = sc.nextInt();
                        sc.nextLine();
                        this.customer.repeatOrder(orderIndex);
                        break;
                    case 14:
                        System.out.print("Enter item name you wish to write a review about: ");
                        itemName = sc.nextLine();
                        System.out.print("Enter the review: ");
                        String review = sc.nextLine();
                        this.customer.submitReview(itemName, review);
                        break;
                    case 15:
                        System.out.print("Enter item name you wish to read reviews about: ");
                        itemName = sc.nextLine();
                        this.customer.viewReviews(itemName);
                        break;
                    case 0:
                        this.logout();
                        return;
                    default:
                        System.out.println("Invalid input\n");
                }
            } catch(InputMismatchException e) {
                System.out.println("Invalid input\n");
            }
        }
    }

    private void logout() {
        System.out.println("Logging out...\n");
        CustomerList.updateCustomer(this.customer);
    }
}
