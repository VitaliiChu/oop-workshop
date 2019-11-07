package checkout;

public class Product {
    int price;
    private final String name;
    Category category;

    public Product(int price, String name, Category category) {
        this.price = price;
        this.name = name;
        this.category = category;
    }

    public Product(int price, String name) {
        this(price, name, null);
    }

    String getName() {
        return name;
    }
}
