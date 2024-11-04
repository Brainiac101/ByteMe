package database;

import logistics.Category;
import logistics.Item;

import java.util.ArrayList;
import java.util.List;

public final class ItemList {
    private static final List<Item> items = new ArrayList<>();

    public static boolean contains(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void addItem(Item item) {
        items.add(item);
    }

    public static List<Item> getItems() {
        return items;
    }

    public static List<Item> getItemsByCategory(Category category) {
        List<Item> itemsByCategory = new ArrayList<>();
        items.forEach(item -> {
            if (item.getCategory().equals(category))
                itemsByCategory.add(item);
        });
        return itemsByCategory;
    }

    public static List<Item> searchItems(String itemName) {
        List<Item> temp = new ArrayList<>();
        final Item[] best = {null};
        items.forEach(item -> {
            if (item.getName().contains(itemName)) {
                if(item.getName().equals(itemName)){
                    best[0] = item;
                }
                temp.add(item);
            }
        });
        temp.remove(best[0]);
        temp.addFirst(best[0]);
        return temp;
    }

    public static Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public static List<Item> getItemsByIncreasingPrice() {
        List<Item> itemsByIncreasingPrice = items;
        itemsByIncreasingPrice = itemsByIncreasingPrice.stream()
                .sorted((i1, i2) -> i1.getPrice() - i2.getPrice())
                .toList();
        return itemsByIncreasingPrice;
    }

    public static List<Item> getItemsByDecreasingPrice() {
        List<Item> itemsByDecreasingPrice = items;
        itemsByDecreasingPrice = itemsByDecreasingPrice.stream()
                .sorted((i1, i2) -> i2.getPrice() - i1.getPrice())
                .toList();
        return itemsByDecreasingPrice;
    }

    public static void updateItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            Item temp = items.get(i);
            if (temp.getName().equals(item.getName()) && temp.getCategory().equals(item.getCategory())) {
                items.set(i, item);
                break;
            }
        }
    }

    public static void removeItem(Item item) {
        items.remove(item);
    }
}
