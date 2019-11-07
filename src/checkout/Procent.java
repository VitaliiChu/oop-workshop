package checkout;

import java.util.List;

public class Procent implements DiscountRule {
    private Discount discount;

    public Procent(Discount discount) {
        this.discount = discount;
    }

    @Override
    public void calcDiscount(Check check) {
        List<Product> products = check.getProducts();
        for (Product p : products) {
            if (p.getName().equals(this.discount.product.getName())) {
                check.addToDiscount((p.price * discount.procent) / 100);
            }
        }
    }
}
