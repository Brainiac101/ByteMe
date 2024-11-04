package logistics;

import database.DeniedOrders;
import database.ItemList;
import database.OrderList;
import users.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Cart {
    private final Customer user;
    private final HashMap<Item, Integer> items;
    private int price;
    private final List<Order> orders;
    private String request;
    private String address;
    private boolean isPaid;

    public Cart(Customer user) {
        this.user = user;
        items = new HashMap<>();
        orders = new ArrayList<>();
    }

    public Cart(Customer user, List<Order> orders) {
        this.user = user;
        items = new HashMap<>();
        this.orders = orders;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addItem(Item item, int quantity) {
        if (quantity > 0 && item.getAvailability() > quantity) {
            item.setAvailability(item.getAvailability() - quantity);
            ItemList.updateItem(item);
            this.items.put(item, quantity);
            this.price += item.getPrice() * quantity;
            System.out.println("Item added to the cart\n");
        } else if (quantity < 0) System.out.println("Please enter valid quantity (must be greater than zero)");
        else System.out.println("Entered quantity exceeds count in Inventory");
    }

    public boolean containsItem(Item item) {
        return items.containsKey(item);
    }

    public void removeItem(Item item) {
        item.setAvailability(item.getAvailability() + this.items.get(item));
        ItemList.updateItem(item);
        this.price -= item.getPrice() * this.items.get(item);
        this.items.remove(item);
    }

    public Order getLastOrder() {
        if (!orders.isEmpty()) return orders.getLast();
        else return null;
    }

    public void cancelOrder() {
        Order order = getLastOrder();
        if (order == null || order.getStatus() != Status.Delivered || order.getStatus() != Status.Cancelled || order.getStatus() != Status.Denied) {
            System.out.println("Order cancellation not possible\n");
            return;
        }
        for (Item i : order.getItems().keySet()) {
            i.setAvailability(i.getAvailability() + this.items.get(i));
            ItemList.updateItem(i);
        }
        orders.remove(order);
        order.setStatus(Status.Cancelled);
        OrderList.removeOrder(order.getId());
        if (order.isPaid()) DeniedOrders.addDeniedOrder(order);
        System.out.println("Order Cancelled\n");
    }

    public void addQuantity(Item item) {
        item.setAvailability(item.getAvailability() + this.items.get(item));
        ItemList.updateItem(item);
        this.price -= item.getPrice() * this.items.get(item);
        this.addItem(item, items.get(item) + 1);
    }

    public void decreaseQuantity(Item item) {
        item.setAvailability(item.getAvailability() + this.items.get(item));
        ItemList.updateItem(item);
        this.price -= item.getPrice() * this.items.get(item);
        this.addItem(item, items.get(item) - 1);
    }

    public void placeOrder(boolean isVIP) {
        int priority = isVIP ? 1 : 0;
        if (!items.isEmpty())
            orders.add(OrderList.addOrder(new Order(items, Status.OrderReceived, priority, request, price, address, isPaid, user)));
    }

    public void placeOrder(boolean isVIP, int index) {
        Order o = orders.get(index);
        int priority = isVIP ? 1 : 0;
        o.setStatus(Status.OrderReceived);
        o.setPriority(priority);
        orders.add(OrderList.addOrder(o));
    }

    public boolean hasBoughtItem(Item item) {
        for (Order o : orders) {
            if (o.getStatus() == Status.OrderReceived) {
                for (Item i : o.getItems().keySet()) {
                    if (item.getName().equals(i.getName())) return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for(Item item : items.keySet()){
            temp.append("Name: ").append(item.getName()).append("\nPrice: ").append(item.getPrice()).append("\nCategory: ").append(item.getCategory()).append("\nQuantity: ").append(items.get(item)).append("\n");
        }
        return "Item List:\n" + temp + "\nRequests: " + this.request + "\nOrder Value: " + this.price + "\n";
    }
}
