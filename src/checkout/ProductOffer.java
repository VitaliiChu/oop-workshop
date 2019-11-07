package checkout;

import java.time.LocalDate;
import java.util.List;

public class ProductOffer extends Offer {
    private Product product;
    private int points;

    public ProductOffer(LocalDate expirationDateAfter, Product product, int points) {
        super(expirationDateAfter);
        this.product = product;
        this.points = points;
    }

    @Override
    protected void scorePoints(Check check) {
        List<Product> products = check.getProducts();
        for (Product p : products) {
            if (p.getName().equals(this.product.getName())) {
                check.addPoints(this.points);
            }
        }
    }
}
