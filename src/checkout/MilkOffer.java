package checkout;

import java.time.LocalDate;

public class MilkOffer extends Offer {
    private Product product;
    private int points;

    public MilkOffer(LocalDate expirationDateAfter, Product product, int points) {
        super(expirationDateAfter);
        this.product = product;
        this.points = points;
    }

    @Override
    protected void calcPoints(Check check) {
        if (product.getName().toLowerCase().equals("milk")) {
            check.addPoints(this.points);
        }
    }
}
