package database;

import logistics.Order;
import logistics.Status;

import java.util.*;

final class orderComparer implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        int temp = o1.getPriority() - o2.getPriority();
        if (temp == -1 || temp == 0) {
            return o1.getId() - o2.getId();
        } else {
            return 1;
        }
    }
}

public final class OrderList {
    private static final PriorityQueue<Order> orderList = new PriorityQueue<>(new orderComparer());

    public static Order addOrder(Order order) {
        if(order.getStatus() == Status.Delivered){
            System.out.println("Order already delivered");
            orderList.remove(order);
            return null;
        }
        order.setId(orderList.size() + 1);
        orderList.offer(order);
        return order;
    }

    public static int getLength() {
        return orderList.size();
    }

    public static Order getTopOrder() {
        return orderList.poll();
    }

    public static Order removeOrder(int id) {
        Order temp;
        Iterator<Order> iterator = orderList.iterator();
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (temp.getId() == id) {
                iterator.remove();
                return temp;
            }
        }
        return null;
    }

    public static List<Order> getPendingOrders() {
        List<Order> orders = new ArrayList<>();
        orderList.forEach(order -> orders.add(order));
        return orders;
    }
}
