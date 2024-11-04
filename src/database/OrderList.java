package database;

import logistics.*;

import java.util.*;

public final class OrderList {
    private static final PriorityQueue<Order> orderList = new PriorityQueue<>((o1, o2) -> {
        if (o1.getPriority() != o2.getPriority()) return Integer.compare(o2.getPriority(), o1.getPriority());
        return Integer.compare(o1.getId(), o2.getId());
    });
    private static int ctr = 0;

    public static Order addOrder(Order order) {
        if (order.getStatus() == Status.Delivered) {
            System.out.println("Order already delivered");
            orderList.remove(order);
            return null;
        }
        order.setId(++ctr);
        orderList.offer(order);
        return order;
    }

    public static Order getOrder(int id) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public static void updateOrder(Order order) {
        for (Order o : orderList) {
            if (o.getId() == order.getId()) {
                orderList.remove(o);
                if (o.getStatus() == Status.Delivered) CompletedOrders.addCompletedOrder(order);
                else if (o.getStatus() == Status.Denied || Status.Cancelled == o.getStatus()) {
                    if (o.isPaid()) DeniedOrders.addDeniedOrder(order);
                } else orderList.add(order);
            }
        }
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