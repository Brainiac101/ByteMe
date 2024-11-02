package database;

import logistics.Order;

import java.util.ArrayList;
import java.util.List;

public final class DeniedOrders {
    private static final List<Order> deniedOrders = new ArrayList<>();

    public static List<Order> getDeniedOrders() {
        return deniedOrders;
    }

    public static void addDeniedOrder(Order order) {
        deniedOrders.add(order);
    }

    public static void removeDeniedOrder(Order order) {
        deniedOrders.remove(order);
    }
}
