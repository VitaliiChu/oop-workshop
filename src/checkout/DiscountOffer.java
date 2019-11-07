package checkout;

import java.time.LocalDate;
import java.util.List;

public class DiscountOffer extends Offer {
    private final Product product;
    private final int discountProcent;

    public DiscountOffer(LocalDate tomorrow, Product product, int discountProcent) {
        super(tomorrow);
        this.product = product;
        this.discountProcent = discountProcent;
    }

    @Override
    protected void scorePoints(Check check) {

    }

    @Override
    protected void getDiscount(Check check) {
        List<Product> products = check.getProducts();
        for (Product p : products) {
            if (p.getName().equals(this.product.getName())) {
                p.price = (p.price * discountProcent)/100;
            }
        }
    }
}
