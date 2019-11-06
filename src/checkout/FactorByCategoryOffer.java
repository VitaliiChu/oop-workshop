package checkout;

import java.time.LocalDate;

public class FactorByCategoryOffer extends Offer {
    private final Category category;
    private final int factor;

    public FactorByCategoryOffer(LocalDate expirationDate, Category category, int factor) {
        super(expirationDate);
        this.category = category;
        this.factor = factor;
    }

    @Override
    protected void calcPoints(Check check) {
        int points = check.getCostByCategory(this.category);
        check.addPoints(points * (this.factor - 1));
    }
}
