package checkout;

public class Discount {
    Product product;
    int procent;

    public Discount(Product product, int procent) {
        this.product = product;
        this.procent = procent;
    }
}
