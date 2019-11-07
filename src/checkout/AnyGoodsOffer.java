package checkout;

import java.time.LocalDate;

public class AnyGoodsOffer extends Offer {
    private final int totalCost;
    private final int points;

    public AnyGoodsOffer(LocalDate expirationDate, int totalCost, int points) {
        super(expirationDate);
        this.totalCost = totalCost;
        this.points = points;
    }

    @Override
    protected void scorePoints(Check check) {
        if (this.totalCost <= check.getTotalCost())
            check.addPoints(this.points);
    }
}
