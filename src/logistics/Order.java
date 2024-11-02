package logistics;

import users.Customer;

import java.util.HashMap;

public final class Order {
    private Customer user;
    private int id;
    private final HashMap<Item, Integer> items;
    private Status status;
    private int priority;
    private final String request;
    private int price;
    private String address;
    private boolean isPaid;

    public Order(int priority, String request){
        this.items = new HashMap<>();
        this.priority = priority;
        this.request = request;
    }

    public Order(HashMap<Item, Integer> items, Status status, int priority, String request, int price, String address, boolean isPaid, Customer user) {
        this.items = items;
        this.status = status;
        this.priority = priority;
        this.request = request;
        this.price = price;
        this.address = address;
        this.isPaid = isPaid;
        this.user = user;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<Item, Integer> getItems() {
        return this.items;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString(){
        StringBuilder temp = new StringBuilder();
        for(Item item : items.keySet()){
            temp.append("Name: ").append(item.getName()).append("\nPrice: ").append(item.getPrice()).append("\nCategory: ").append(item.getCategory()).append("\nQuantity: ").append(items.get(item)).append("\n");
        }
        return "Order id: " + this.id + "\nItem List:\n" + temp + "Status: " + this.status + "\nRequests: " + this.request + "\nPrice: " + this.price;
    }
}
