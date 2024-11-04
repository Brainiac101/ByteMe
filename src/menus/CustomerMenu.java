package menus;

import database.*;
import logistics.*;
import users.*;

import java.util.InputMismatchException;

public class CustomerMenu extends Menu {
    protected final Customer customer;

    public CustomerMenu(Customer customer) {
        this.customer = customer;
    }

    private void display() {
        System.out.println("Welcome " + customer.getUsername() + "!");
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
        System.out.println("• 16 for Upgrading to VIP");
        System.out.println("• 0 for Logging out");
    }

    protected Category inputCategory() {
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

    protected void placeOrder() {
        System.out.println("• 1 to set the delivery address automatically");
        System.out.println("• 2 to set the delivery address manually");
        System.out.println("• 0 to return to menu");
        String address;
        int temp = sc.nextInt();
        sc.nextLine();
        switch (temp) {
            case 1:
                address = this.customer.getAddress();
                break;
            case 2:
                System.out.print("Enter address: ");
                address = sc.nextLine();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid input\n");
                return;
        }
        System.out.println("• 1 to pay using cash");
        System.out.println("• 2 to pay using UPI");
        System.out.println("• 3 to pay using Net banking/Cards");
        System.out.println("• 0 to return to menu");
        boolean isPaid;
        temp = sc.nextInt();
        sc.nextLine();
        switch (temp) {
            case 1:
                isPaid = false;
                break;
            case 2, 3:
                isPaid = true;
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid input\n");
                return;
        }
        System.out.println("Order will be delivered to " + address + "\n");
        this.customer.checkout(address, isPaid);
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
                                System.out.println("Specification added to the order\n");
                            case 2:
                                this.placeOrder();
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid input\n");
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
                    case 16:
                        System.out.println("Upgrading status to VIP");
                        CustomerList.updateCustomer(new VIP(this.customer.getUsername(), this.customer.getPassword(), this.customer.getAddress(), this.customer.getCart()));
                        System.out.println("Please login again to ensure that VIP status has been enabled\n");
                        return;
                    case 0:
                        this.logout();
                        return;
                    default:
                        System.out.println("Invalid input\n");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input\n");
            }
        }
    }

    private void logout() {
        System.out.println("Logging out...\n");
        CustomerList.updateCustomer(this.customer);
    }
}
