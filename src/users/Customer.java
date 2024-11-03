package users;

import database.ItemList;
import logistics.*;

import java.util.List;

public class Customer extends User {
    protected Cart cart;
    protected String address;

    public Customer(String username, String password, String address) {
        super(username, password);
        cart = new Cart(this);
        this.address = address;
    }

    public Customer(String username, String password, String address, Cart cart) {
        super(username, password);
        this.cart = cart;
        this.address = address;
    }

    public Cart getCart() {
        return cart;
    }

    public String getAddress() {
        return address;
    }

    public void viewMenu() {
        List<Item> temp = ItemList.getItems();
        if (temp != null && !temp.isEmpty()) {
            for (Item item : temp) {
                System.out.println(item);
                System.out.println();
            }
        } else System.out.println("No items found\n");
    }

    public void searchByName(String name) {
        Item temp = ItemList.getItemByName(name);
        if (temp != null) {
            System.out.println(temp);
            System.out.println();
        }
        else System.out.println("No Products with name " + name + " found\n");
    }

    public void searchByCategory(Category category) {
        List<Item> temp = ItemList.getItemsByCategory(category);
        for (int i = 0; i < temp.size(); i++) {
            Item item = temp.get(i);
            System.out.println(item);
            System.out.println();
        }
    }

    public void getItemsByIncreasingPrice() {
        for (Item item : ItemList.getItemsByIncreasingPrice()) {
            System.out.println(item);
            System.out.println();
        }
    }

    public void getItemsByDecreasingPrice() {
        for (Item item : ItemList.getItemsByDecreasingPrice()) {
            System.out.println(item);
            System.out.println();
        }
    }

    public void addItem(String name, int quantity) {
        Item item = ItemList.getItemByName(name);
        if (item != null) {
            cart.addItem(item, quantity);
        }
        else System.out.println("No item with name " + name + " found\n");
    }

    public void increaseQuantity(String name) {
        Item item = ItemList.getItemByName(name);
        if (item != null) {
            cart.addQuantity(item);
            System.out.println("Quantity increased by 1\n");
        }
        else System.out.println("No item with name " + name + " found\n");
    }

    public void decreaseQuantity(String name) {
        Item item = ItemList.getItemByName(name);
        if (item != null) {
            cart.decreaseQuantity(item);
            System.out.println("Quantity decreased by 1\n");
        }
        else System.out.println("No item with name " + name + " found\n");
    }

    public void removeItem(String name) {
        Item item = ItemList.getItemByName(name);
        if (item != null && cart.containsItem(item)) {
            cart.removeItem(item);
            System.out.println("Item removed!!\n");
        }
        else System.out.println("No item with name " + name + " found\n");
    }

    public void viewCart() {
        System.out.println(cart);
    }

    public void addSpecification(String request) {
        cart.setRequest(request);
    }

    public void checkout(String address, boolean isPaid) {
        this.cart.setAddress(address);
        this.cart.setPaid(isPaid);
        this.cart.placeOrder(false);
        cart = new Cart(this, cart.getOrders());
    }

    public void viewStatus() {
        Order o = this.cart.getLastOrder();
        if(o != null && (o.getStatus() != Status.Cancelled || o.getStatus() != Status.Delivered)) {
            System.out.println(o);
            System.out.println();
        }
        else System.out.println("No order found whose status can be displayed\n");
    }

    public void cancelOrder() {
        this.cart.cancelOrder();
    }

    public void viewOrderHistory() {
        if(this.cart.getOrders().isEmpty()) System.out.println("No orders found\n");
        int i = 1;
        for (Order o : this.cart.getOrders()) {
            System.out.println(i + ". " +o);
            i++;
            System.out.println();
        }
    }

    public void repeatOrder(int index) {
        this.cart.placeOrder(false, index - 1);
    }

    public void submitReview(String itemName, String review){
        Item item = ItemList.getItemByName(itemName);
        if(item != null && cart.hasBoughtItem(item)) {
            item.addReview(review);
            System.out.println("Review Added\n");
        }
        else System.out.println("No item with name " + itemName + " found\n");
    }

    public void viewReviews(String itemName) {
        Item item = ItemList.getItemByName(itemName);
        if(item != null) {
            for (String s : item.getReviews()) System.out.println("• " + s);
            System.out.println();
        }
        else System.out.println("No item with name " + itemName + " found\n");
    }
}