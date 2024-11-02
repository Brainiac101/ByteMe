package users;

import database.CompletedOrders;
import database.DeniedOrders;
import database.ItemList;
import database.OrderList;
import logistics.Category;
import logistics.Item;
import logistics.Order;
import logistics.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Admin extends User{
    public Admin(String username, String password) {
        super(username, password);
    }

    public void addItem(String name, int price, Category category, int availability){
        Item item = new Item(name, price, category, availability);
        ItemList.addItem(item);
        System.out.println("Item added!! \n");
    }

    public void updateItemPrice(Item item, int price){
        item.setPrice(price);
        ItemList.updateItem(item);
    }

    public void updateItemAvailability(Item item, int availability){
        item.setAvailability(availability);
        ItemList.updateItem(item);
    }

    public void deleteItem(String name){
        Item item = ItemList.getItemByName(name);
        if(item == null){
            System.out.println("Item not found\n");
            return;
        }
        for(Order o: OrderList.getPendingOrders()) {
            o.setStatus(Status.Denied);
            OrderList.updateOrder(o);
        }
        ItemList.removeItem(item);
        System.out.println("Item removed\n");
    }

    public void viewPendingOrders(){
        for(Order o: OrderList.getPendingOrders()){
            System.out.println(o);
            System.out.println();
        }
    }

    public void updateOrderStatus(int id, Status status){
        Order temp = OrderList.getOrder(id);
        if(temp != null ) {
            temp.setStatus(status);
            if(temp.getStatus() == Status.Delivered) CompletedOrders.addCompletedOrder(temp);
            else if(temp.getStatus() == Status.Denied) {
                if(temp.isPaid()) DeniedOrders.addDeniedOrder(temp);
            }
            else OrderList.updateOrder(temp);
            System.out.println("Order Status updated\n");
        }
        else System.out.println("Order not found\n");
    }

    public void getReport(){
        HashMap<Item, Integer> map = new HashMap<>();
        List<Order> temp = CompletedOrders.getCompletedOrders();
        int earnings = 0;
        for(Order o: temp){
            earnings += o.getPrice();
            HashMap<Item, Integer> cart = o.getItems();
            for(Item i: cart.keySet()){
                if(map.containsKey(i)) map.put(i, map.get(i) + cart.get(i));
                else map.put(i, cart.get(i));
            }
        }
        List<Item> top = new ArrayList<>();
        for(Item i: map.keySet()){
            if(top.size() == 3){
                Item lowest = top.get(0);
                if(map.get(top.get(1)) < map.get(lowest)) lowest = top.get(1);
                if(map.get(top.get(2)) < map.get(lowest)) lowest = top.get(2);
                if(map.get(lowest) < map.get(i)){
                    top.remove(lowest);
                    top.add(i);
                }
            }
            else top.add(i);
        }
        System.out.println("Orders Processed: " + temp.size());
        System.out.println("Earnings: " + earnings);
        System.out.println("Popular products of the day: ");
        for(Item i: top) System.out.println(i.getName() + " - " + map.get(i));
    }
}
