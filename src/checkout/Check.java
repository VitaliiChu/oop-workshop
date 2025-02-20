package checkout;

import java.util.ArrayList;
import java.util.List;

public class Check {
    private List<Product> products = new ArrayList<>();
    private int points = 0;
    private int discount = 0;

    public int getTotalCost() {
        int totalCost = 0;
        for (Product product : this.products) {
            totalCost += product.price;
        }
        return totalCost - discount;
    }

    void addProduct(Product product) {
        products.add(product);
    }

    void addToDiscount(int discount) {
        this.discount += discount;
    }

    public int getTotalPoints() {
        return getTotalCost() + points;
    }

    List<Product> getProducts() {
        return products;
    }

    void addPoints(int points) {
        this.points += points;
    }

    int getCostByCategory(Category category) {
        return products.stream()
                .filter(p -> p.category == category)
                .mapToInt(p -> p.price)
                .reduce(0, (a, b) -> a + b);
    }
}
