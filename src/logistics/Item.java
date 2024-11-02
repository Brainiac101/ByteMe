package logistics;

import java.util.ArrayList;
import java.util.List;

public final class Item {
    private final String name;
    private int price;
    private final Category category;
    private int availability;
    private final List<String> reviews;

    public Item(String name, int price, Category category, int availability) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.availability = availability;

        this.reviews = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void addReview(String review) {
        this.reviews.add(review);
    }

    public List<String> getReviews() {
        return reviews;
    }

    public String toString() {
        return "Name: " + this.name + "\nPrice: " + this.price + "\nCategory: " + this.category + "\nAvailability: " + this.availability;
    }
}
